package ru.nbakaev.hishop.customobjects;

import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.nbakaev.hishop.constants.CUSTOM_FIELD_TYPES;
import ru.nbakaev.hishop.customobjects.entity.CustomField;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomFieldProcessingService {

    private final MongoOperations mongoTemplate;

    @Autowired
    public CustomFieldProcessingService(final MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    /**
     * get new/old/changed fields
     *
     * @param oldFields
     * @param newFields
     * @param deletedFields
     * @param changedFields
     * @param newCreatedFields
     */
    public void processFieldsNewOldChanged(List<CustomField> oldFields, List<CustomField> newFields,
                                           List<CustomField> deletedFields, List<CustomField> changedFields, List<CustomField> newCreatedFields) {

        for (CustomField newField : oldFields) {
            boolean deletedField = true;
            for (CustomField oldField : newFields) {
                if (newField.getFieldId().equals(oldField.getFieldId())) deletedField = false;
            }
            if (deletedField) deletedFields.add(newField);
        }

        for (CustomField newField : newFields) {
            boolean newCreatedField = true;
            for (CustomField oldField : oldFields) {
                if (newField.getFieldId().equals(oldField.getFieldId())) newCreatedField = false;
            }
            if (newCreatedField) newCreatedFields.add(newField);
        }

        for (CustomField newField : newFields) {
            for (CustomField oldField : oldFields) {
                if (newField.getFieldId().equals(oldField.getFieldId())) {
                    if (!newField.equals(oldField)) {
                        changedFields.add(newField);
                    }
                }
            }
        }

    }

    public void processFields(List<CustomField> oldFields, List<CustomField> newFields, String collection) {
        if (oldFields.equals(newFields)) return;

        // fields that was deleted from prev
        List<CustomField> deletedFields = new ArrayList<>();

        // fields that was changed(type, name, default value etc...)
        List<CustomField> changedFields = new ArrayList<>();

        // fields that new and was created
        List<CustomField> newCreatedFields = new ArrayList<>();

        processFieldsNewOldChanged(oldFields, newFields, deletedFields, changedFields, newCreatedFields);

        // if we have not any changes
        if (deletedFields.size() == 0 && newCreatedFields.size() == 0 && changedFields.size() == 0) return;

        if (collection == null || collection.length() == 0) {
            throw new RuntimeException("YOU SHOULD TELL COLLECTION NAME");
        }

        // DELETE some fields
        for (CustomField field : deletedFields) {
            Criteria criteriaDelete = new Criteria();
            criteriaDelete.and("customFields.fieldId").is(field.getFieldId());


            Update update = new Update().pull("customFields", new BasicDBObject("fieldId", field.getFieldId()));
            Query query = new Query(criteriaDelete);
            mongoTemplate.updateMulti(query, update, collection);
        }

        // CREATE new fields
        for (CustomField field : newCreatedFields) {
            Criteria criteriaDelete = new Criteria();

            Update update = new Update().push("customFields", field);
            Query query = new Query(criteriaDelete);
            mongoTemplate.updateMulti(query, update, collection);
        }

        // UPDATE field
        for (CustomField field : changedFields) {
            Criteria criteriaDelete = new Criteria();
            criteriaDelete.and("customFields.fieldId").is(field.getFieldId());

            Update update = new Update();
            update.set("customFields.$.name", field.getName());
            update.set("customFields.$.description", field.getDescription());
            update.set("customFields.$.validPattern", field.getValidPattern());
            update.set("customFields.$.type", field.getType());
            update.set("customFields.$.style", field.getStyle());
            update.set("customFields.$.required", field.isRequired());
            update.set("customFields.$.hidden", field.isHidden());

            // check dictionary fields for values changes
            if (field.getType().equals(CUSTOM_FIELD_TYPES.DICTIONARY)) {
                if (field.getValue() != null && field.getValue().getDictVal() != null && field.getValue().getDictVal().getValues() != null) {
                    update.set("customFields.$.value.dictVal.values", field.getValue().getDictVal().getValues());
                }
            }

            Query query = new Query(criteriaDelete);
            mongoTemplate.updateMulti(query, update, collection);
        }
    }


}

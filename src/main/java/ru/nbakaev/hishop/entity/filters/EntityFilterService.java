package ru.nbakaev.hishop.entity.filters;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import ru.nbakaev.hishop.entity.filters.requestdto.BasicFilterRequestDto;
import ru.nbakaev.hishop.entity.filters.requestdto.RegexBuilderNode;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 4/15/2016.
 * All Rights Reserved
 */
@Service
public class EntityFilterService {

    /**
     * @param basicFilterDto
     * @return spring MongoDB Criteria
     */
    public Criteria getCriteriaFromAbstractFilter(BasicFilterRequestDto basicFilterDto) {

        Criteria criteria = new Criteria();

        // select by createdDate
        if (basicFilterDto.createdDateFrom != null && basicFilterDto.getCreatedDateTo() == null) {
            criteria.and("createdInfo.createdDate").gte(basicFilterDto.createdDateFrom);
        } else if (basicFilterDto.createdDateFrom != null && basicFilterDto.getCreatedDateTo() != null) {
            criteria.and("createdInfo.createdDate").gte(basicFilterDto.createdDateFrom).lte(basicFilterDto.createdDateTo);
        } else if (basicFilterDto.createdDateFrom == null && basicFilterDto.getCreatedDateTo() != null) {
            criteria.and("createdInfo.createdDate").lte(basicFilterDto.createdDateTo);
        }

        // select objects by regular expression
        if (basicFilterDto.isUseRegex() && basicFilterDto.getRegexBuilderNodes() != null && basicFilterDto.getRegexBuilderNodes().size() > 0) {
            for (RegexBuilderNode node : basicFilterDto.getRegexBuilderNodes()) {

                if (node.getFieldName() != null && node.getPattern() != null && node.getValue() == null) {
                    criteria.and(node.getFieldName()).regex(node.getPattern());

                } else if (node.getFieldName() != null && node.getValue() != null && node.getPattern() == null) {
                    criteria.and(node.getFieldName()).is(node.getValue());
                }
            }
        }

        return criteria;
    }

    /**
     * @param basicFilterDto
     * @param criteria
     * @return spring MongoDB query
     */
    public Query getQueryFromAbstractFilter(BasicFilterRequestDto basicFilterDto, Criteria criteria) {
        Query query;

        // mongodb full text search
        if (!basicFilterDto.isUseFullTextSearch()) {
            query = new Query(criteria);
        } else {
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(basicFilterDto.getFullTextSearchRequest());
            query = TextQuery.queryText(textCriteria)
                    .sortByScore()
                    .addCriteria(criteria);
        }

        // reverse sort
        if (basicFilterDto.isSortDESCbyCreatedDate() && !basicFilterDto.isUseCustomSort()) {
            query.with(new Sort(Sort.Direction.DESC, "createdInfo.createdDate"));
        }

        // sort by custom field
        if (basicFilterDto.isUseCustomSort()) {
            if (basicFilterDto.isReverseSortDirectionByCustomField()) {
                query.with(new Sort(Sort.Direction.DESC, basicFilterDto.getCustomSortField()));
            } else {
                query.with(new Sort(Sort.Direction.ASC, basicFilterDto.getCustomSortField()));
            }
        }

        // get only some part of resulted entities
        if (basicFilterDto.isUsePagination()) {
            query.skip(basicFilterDto.getRecordFrom());
            query.limit(basicFilterDto.getRecordTo() - basicFilterDto.getRecordFrom());
        }

        // return from data base only some fields
        if (basicFilterDto.isUseFieldsPartly()) {
            basicFilterDto.getPartlyFields().forEach(x -> query.fields().include(x));
        }

        // exclude some fields from result
        if (basicFilterDto.isUseExcludeFieldsPartly()) {
            basicFilterDto.getPartlyExcludeFields().forEach(x -> query.fields().exclude(x));
        }

        return query;
    }

    /**
     * If we want to just count all result by some criteria
     * with mongo fullText support
     *
     * @param basicFilterDto
     * @param criteria
     * @param objectType
     * @param ops            mongoTemplate
     * @return number or matching documents
     */
    public long countResultFromCriteria(BasicFilterRequestDto basicFilterDto, Criteria criteria, Class objectType, MongoOperations ops) {
        long resultEntity = 0;
        Query query;

        // count of entities with full text search
        if (!basicFilterDto.isUseFullTextSearch()) {
            query = new Query(criteria);
        } else {
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(basicFilterDto.getFullTextSearchRequest());
            query = TextQuery.queryText(textCriteria)
                    .sortByScore()
                    .addCriteria(criteria);
        }

        resultEntity = ops.count(query, objectType);
        return resultEntity;
    }

}

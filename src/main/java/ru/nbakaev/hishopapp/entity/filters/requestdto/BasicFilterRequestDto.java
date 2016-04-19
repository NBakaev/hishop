package ru.nbakaev.hishopapp.entity.filters.requestdto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 4/15/2016.
 * All Rights Reserved
 */
@ApiModel("This is base filter for all object filters")
public class BasicFilterRequestDto implements Serializable {

    @ApiModelProperty("Show only objects that created after")
    public Date createdDateFrom = null;

    @ApiModelProperty("Show only objects that created until")
    public Date createdDateTo = null;

    @ApiModelProperty("Use pagination for result. Show only some part of data ")
    protected boolean usePagination;

    @ApiModelProperty("Pagination record from")
    protected int recordFrom;

    @ApiModelProperty("Pagination record to")
    protected int recordTo;

    @ApiModelProperty("True , if you WANT to receive ONLY some fields in response. Response can be quicker ")
    protected boolean useFieldsPartly = false;
    protected List<String> partlyFields = new ArrayList<>();

    @ApiModelProperty("True , if you DON'T WANT to receive some fields in response. Response can be quicker ")
    protected boolean useExcludeFieldsPartly = false;
    protected List<String> partlyExcludeFields = new ArrayList<>();


    @ApiModelProperty("Is sort result by some custom field name ")
    protected boolean useCustomSort = false;
    @ApiModelProperty("  sort result by field name such as `firstName` for customer ")
    protected String customSortField;
    @ApiModelProperty("Reverse sorting results ")
    protected boolean reverseSortDirectionByCustomField;

    @ApiModelProperty("Sort by latest created objects to the top ")
    protected boolean sortDESCbyCreatedDate;


    @ApiModelProperty("Use regex `regexBuilderNodes` field ")
    protected boolean useRegex = false;
    @ApiModelProperty("Regex list of patterns")
    protected List<RegexBuilderNode> regexBuilderNodes = new ArrayList<>();

    @ApiModelProperty("Is use search by name (for example name for good) etc ")
    protected boolean useFullTextSearch = false;
    @ApiModelProperty("Text to search in resource data ")
    protected String fullTextSearchRequest;

    @ApiModelProperty(" if we want to only count resulted entity - we do it and return result without objects. That's faster than get objects ")
    protected boolean onlyCount = false;

    public boolean isOnlyCount() {
        return onlyCount;
    }

    public void setOnlyCount(boolean onlyCount) {
        this.onlyCount = onlyCount;
    }

    public Date getCreatedDateFrom() {
        return createdDateFrom;
    }

    public void setCreatedDateFrom(Date createdDateFrom) {
        this.createdDateFrom = createdDateFrom;
    }

    public Date getCreatedDateTo() {
        return createdDateTo;
    }

    public void setCreatedDateTo(Date createdDateTo) {
        this.createdDateTo = createdDateTo;
    }

    public boolean isUseFullTextSearch() {
        return useFullTextSearch;
    }

    public void setUseFullTextSearch(boolean useFullTextSearch) {
        this.useFullTextSearch = useFullTextSearch;
    }

    public String getFullTextSearchRequest() {
        return fullTextSearchRequest;
    }

    public void setFullTextSearchRequest(String fullTextSearchRequest) {
        this.fullTextSearchRequest = fullTextSearchRequest;
    }

    public boolean isUseRegex() {
        return useRegex;
    }

    public void setUseRegex(boolean useRegex) {
        this.useRegex = useRegex;
    }

    public List<RegexBuilderNode> getRegexBuilderNodes() {
        return regexBuilderNodes;
    }

    public void setRegexBuilderNodes(List<RegexBuilderNode> regexBuilderNodes) {
        this.regexBuilderNodes = regexBuilderNodes;
    }

    public boolean isUseCustomSort() {
        return useCustomSort;
    }

    public void setUseCustomSort(boolean useCustomSort) {
        this.useCustomSort = useCustomSort;
    }

    public String getCustomSortField() {
        return customSortField;
    }

    public void setCustomSortField(String customSortField) {
        this.customSortField = customSortField;
    }

    public boolean isReverseSortDirectionByCustomField() {
        return reverseSortDirectionByCustomField;
    }

    public void setReverseSortDirectionByCustomField(boolean reverseSortDirectionByCustomField) {
        this.reverseSortDirectionByCustomField = reverseSortDirectionByCustomField;
    }

    public boolean isSortDESCbyCreatedDate() {
        return sortDESCbyCreatedDate;
    }

    public void setSortDESCbyCreatedDate(boolean sortDESCbyCreatedDate) {
        this.sortDESCbyCreatedDate = sortDESCbyCreatedDate;
    }

    public boolean isUseFieldsPartly() {
        return useFieldsPartly;
    }

    public void setUseFieldsPartly(boolean useFieldsPartly) {
        this.useFieldsPartly = useFieldsPartly;
    }

    public List<String> getPartlyFields() {
        return partlyFields;
    }

    public void setPartlyFields(List<String> partlyFields) {
        this.partlyFields = partlyFields;
    }

    public boolean isUseExcludeFieldsPartly() {
        return useExcludeFieldsPartly;
    }

    public void setUseExcludeFieldsPartly(boolean useExcludeFieldsPartly) {
        this.useExcludeFieldsPartly = useExcludeFieldsPartly;
    }

    public List<String> getPartlyExcludeFields() {
        return partlyExcludeFields;
    }

    public void setPartlyExcludeFields(List<String> partlyExcludeFields) {
        this.partlyExcludeFields = partlyExcludeFields;
    }

    public boolean isUsePagination() {
        return usePagination;
    }

    public void setUsePagination(boolean usePagination) {
        this.usePagination = usePagination;
    }

    public int getRecordFrom() {
        return recordFrom;
    }

    public void setRecordFrom(int recordFrom) {
        this.recordFrom = recordFrom;
    }

    public int getRecordTo() {
        return recordTo;
    }

    public void setRecordTo(int recordTo) {
        this.recordTo = recordTo;
    }
}

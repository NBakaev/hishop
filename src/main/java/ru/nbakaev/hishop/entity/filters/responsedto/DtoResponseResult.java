package ru.nbakaev.hishop.entity.filters.responsedto;

import ru.nbakaev.hishop.entity.BasicClassAbstract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/28/2016.
 * All Rights Reserved
 */
public abstract class DtoResponseResult {

    /**
     * All objects relevant to some search criteria
     * Different from returnedObjectsNumber
     * when used pagination
     */
    private long relevantObjectsNumber;
    private long returnedObjectsNumber;
    private List<? extends BasicClassAbstract> resultedObjects = new ArrayList<>();


    public long getRelevantObjectsNumber() {
        return relevantObjectsNumber;
    }

    public void setRelevantObjectsNumber(long relevantObjectsNumber) {
        this.relevantObjectsNumber = relevantObjectsNumber;
    }

    public long getReturnedObjectsNumber() {
        return returnedObjectsNumber;
    }

    public void setReturnedObjectsNumber(long returnedObjectsNumber) {
        this.returnedObjectsNumber = returnedObjectsNumber;
    }

    public List<? extends BasicClassAbstract> getResultedObjects() {
        return resultedObjects;
    }

    public void setResultedObjects(List<? extends BasicClassAbstract> resultedObjects) {
        this.resultedObjects = resultedObjects;
    }
}

package ru.nbakaev.hishop.utils.parsing.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/21/2016.
 * All Rights Reserved
 */
public class SheetData {
    private Object[] headers;
    private List<Object[]> data = new ArrayList<>();
    private XSSFSheet sheet;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getHeaders() {
        return headers;
    }

    public void setHeaders(Object[] headers) {
        this.headers = headers;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public XSSFSheet getSheet() {
        return sheet;
    }

    public void setSheet(XSSFSheet sheet) {
        this.sheet = sheet;
    }
}

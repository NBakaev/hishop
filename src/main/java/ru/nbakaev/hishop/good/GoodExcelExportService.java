package ru.nbakaev.hishop.good;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nbakaev.hishop.utils.parsing.excel.ExcelCommonService;
import ru.nbakaev.hishop.entity.filters.responsedto.GoodResultResponseDto;
import ru.nbakaev.hishop.entity.filters.requestdto.GoodFilterRequestDto;
import ru.nbakaev.hishop.utils.parsing.excel.BasicExcelObjectPrinter;
import ru.nbakaev.hishop.utils.parsing.excel.SheetData;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 3/28/2016.
 * All Rights Reserved
 */
@Service
public class GoodExcelExportService {

    @Autowired
    private ExcelCommonService excelCommonService;

    @Autowired
    private GoodRepository goodRepository;


    public byte[] getGoodsExcel(GoodFilterRequestDto goodEntityDto) {

        GoodResultResponseDto goodDtoResult = goodRepository.getGoodsByDto(goodEntityDto);

        BasicExcelObjectPrinter basicExcelObjectPrinter = new BasicExcelObjectPrinter("Goods", excelCommonService);
        basicExcelObjectPrinter.createMetaSheet(goodEntityDto, goodDtoResult.getRelevantObjectsNumber(), new Date());

        SheetData sheetData = basicExcelObjectPrinter.getMainSheet();

        XSSFWorkbook workbook = basicExcelObjectPrinter.getWorkbook();

        List<Object[]> data = sheetData.getData();

        int i = 0;

        sheetData.setHeaders(new Object[]{"№", "ID",
                "Название",
                "Цена",
                "Доступное число",
                "Продано",
                "Описание",
                "Дата создания"
        });

        for (Good good : (List<Good>) goodDtoResult.getResultedObjects()) {
            i++;
            data.add(new Object[]{String.valueOf(i),
                    good.getId(),
                    good.getName(),
                    good.getPrice(),
                    good.getNumberAvailable(),
                    good.getNumberSold(),
                    good.getDescription(),
                    (good.getCreatedInfo() == null || good.getCreatedInfo().getCreatedDate() == null) ? "NULL" : good.getCreatedInfo().getCreatedDate()
            });
        }

//        basicExcelObjectPrinter.setFunction(x -> {
//
//            int rownum = x.getRowNum();
//            int cellnum = x.getColNum();
//            Object obj = x.getObject();
//            Cell cell = x.getCell();
//
//            // this is body
//            if (rownum > 1) {
//
//            }
//
//            return true;
//        });

        basicExcelObjectPrinter.build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            workbook.write(outputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return outputStream.toByteArray();
    }


}

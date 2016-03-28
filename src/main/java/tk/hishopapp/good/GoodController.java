package tk.hishopapp.good;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.hishopapp.auth.UserAccountRoles;
import tk.hishopapp.dto.GoodEntityDto;
import tk.hishopapp.utils.parsing.excel.BasicExcelObjectPrinter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/good")
@Api("Goods")
public class GoodController {

    private final GoodRepository goodRepository;
    private final GoodExcelExportService goodExcelExportService;


    @Autowired
    public GoodController(final GoodRepository goodRepository, final GoodExcelExportService goodExcelExportService) {
        this.goodRepository = goodRepository;
        this.goodExcelExportService = goodExcelExportService;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    Good addGood(@RequestBody Good good, HttpServletRequest request) {
        goodRepository.createGood(good);
        return good;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Good> getAllGoods() {
        return goodRepository.getAllGoods();
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    Good updateGood(@RequestBody Good good, HttpServletRequest request) {
        goodRepository.updateGood(good);
        return good;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteGoodById(@PathVariable("id") String id) {
        goodRepository.deleteGoodById(id);
    }

    @RequestMapping(value = "category/index", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Good> getAllGoodsInIndexPage() {
        return goodRepository.getAllGoodsOnIndexPage();
    }

    // TODO:   @Secured({UserAccountRoles.ROLE_ADMIN})
    @ApiOperation(value = "get goods in excel by criteria (builder)")
    @RequestMapping(value = "excel/download", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<byte[]> getGoodsInExcel(@RequestBody GoodEntityDto goodEntityDto, HttpServletResponse response, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(BasicExcelObjectPrinter.EXCEL_MIME_TYPE));

        ResponseEntity responseEntity = new ResponseEntity(goodRepository.getGoodsByDto(goodEntityDto), headers, HttpStatus.ACCEPTED);
        return responseEntity;
    }

    // TODO:   @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "excel/download.xlsx", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<byte[]> getAllGoodsInExcel() {

        GoodEntityDto goodEntityDto = new GoodEntityDto();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(BasicExcelObjectPrinter.EXCEL_MIME_TYPE));

        ResponseEntity responseEntity = new ResponseEntity(goodExcelExportService.getGoodsExcel(goodEntityDto), headers, HttpStatus.ACCEPTED);
        return responseEntity;
    }

}
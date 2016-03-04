package tk.hishopapp.good.search;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.hishopapp.good.Good;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/good/search")
@Api("Goods search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    @ApiOperation(value = "Search goods", notes = "full text search in good object in fields 'name' and 'description'")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Good> searchGoods(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return searchService.findAllGoods(searchRequest);
    }

}
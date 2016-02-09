package tk.hishopapp.good;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    public GoodController(final GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

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

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    Good updateGood(@RequestBody Good good, HttpServletRequest request) {
        goodRepository.updateGood(good);
        return good;
    }

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

}
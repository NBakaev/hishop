package ru.hishop.purchaseorder;

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
@RequestMapping("/purchase")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    PurchaseOrder addNewGood(@RequestBody PurchaseOrder purchaseOrder, HttpServletRequest request) {
        purchaseOrderRepository.createNewPurchaseOrder(purchaseOrder);
        return purchaseOrder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PurchaseOrder> getAllPurchases() {
        return purchaseOrderRepository.getAllPurchaseOrder();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    PurchaseOrder updateGood(@RequestBody PurchaseOrder purchaseOrder, HttpServletRequest request) {
        purchaseOrderRepository.updatePurchaseOrder(purchaseOrder);
        return purchaseOrder;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteGoodById(@PathVariable("id") String id) {
        purchaseOrderRepository.deletePurchaseOrderById(id);
    }

}
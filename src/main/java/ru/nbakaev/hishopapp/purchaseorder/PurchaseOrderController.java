package ru.nbakaev.hishopapp.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nbakaev.hishopapp.auth.UserAccountRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/purchase")
public class PurchaseOrderController {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    public PurchaseOrderController(final PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Secured({UserAccountRoles.ROLE_USER, UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    PurchaseOrder addNewPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder, HttpServletRequest request) {
        purchaseOrderRepository.createNewPurchaseOrder(purchaseOrder);
        return purchaseOrder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PurchaseOrder> getAllPurchases() {
        return purchaseOrderRepository.getAllPurchaseOrder();
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    PurchaseOrder updatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder, HttpServletRequest request) {
        purchaseOrderRepository.updatePurchaseOrder(purchaseOrder);
        return purchaseOrder;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deletePurchaseById(@PathVariable("id") String id) {
        purchaseOrderRepository.deletePurchaseOrderById(id);
    }

}
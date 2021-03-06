package ru.nbakaev.hishop.purchaseorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nbakaev.hishop.auth.UserAccountRoles;
import ru.nbakaev.hishop.configs.InvalidRequestException;
import ru.nbakaev.hishop.users.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/purchase")
public class PurchaseOrderController {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CurrentUser currentUser;

    @Autowired
    public PurchaseOrderController(final PurchaseOrderRepository purchaseOrderRepository, final CurrentUser currentUser) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.currentUser = currentUser;
    }

    @Secured({UserAccountRoles.ROLE_USER, UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    PurchaseOrder addNewPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder, HttpServletResponse response) {

        if (purchaseOrder.getGoodList() == null || purchaseOrder.getGoodList().size() == 0){
            throw new InvalidRequestException("You must set goods");
        }

        purchaseOrderRepository.createNewPurchaseOrder(purchaseOrder);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return purchaseOrder;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PurchaseOrder> getAllPurchases() {
        return purchaseOrderRepository.getAllPurchaseOrder();
    }

    @Secured({UserAccountRoles.ROLE_USER})
    @RequestMapping(value = "my", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PurchaseOrder> getPurchaseOrderForUser() {
        return purchaseOrderRepository.getPurchaseOrderForUser(currentUser.getCurrentUser().getId());
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
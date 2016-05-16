package ru.nbakaev.hishop.purchaseorder.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nbakaev.hishop.auth.UserAccountRoles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/purchase_status")
public class PurchaseStatusController {

    private final PurchaseStatusRepository purchaseStatusRepository;

    @Autowired
    public PurchaseStatusController(final PurchaseStatusRepository purchaseStatusRepository) {
        this.purchaseStatusRepository = purchaseStatusRepository;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    PurchaseStatus createNewPurchaseStatus(@RequestBody PurchaseStatus purchaseOrder, HttpServletRequest request) {
        purchaseStatusRepository.createNewPurchaseStatus(purchaseOrder);
        return purchaseOrder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PurchaseStatus> getAllPurchaseStatuses() {
        return purchaseStatusRepository.getAllPurchaseStatuses();
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    PurchaseStatus updatePurchaseStatus(@RequestBody PurchaseStatus purchaseOrder, HttpServletRequest request) {
        purchaseStatusRepository.updatePurchaseStatus(purchaseOrder);
        return purchaseOrder;
    }

    @Secured({UserAccountRoles.ROLE_ADMIN})
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deletePurchaseStatusById(@PathVariable("id") String id) {
        purchaseStatusRepository.deletePurchaseStatusById(id);
    }

}
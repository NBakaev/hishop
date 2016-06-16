package ru.nbakaev.hishop.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nbakaev.hishop.auth.UserAccountRoles;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */
@Controller
@RequestMapping("/api/v1/settings")
@Secured({UserAccountRoles.ROLE_ADMIN})
public class ShopSettingsController {

    private final ShopSettingsRepository shopSettingsRepository;

    @Autowired
    public ShopSettingsController(final ShopSettingsRepository shopSettingsRepository) {
        this.shopSettingsRepository = shopSettingsRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public
    @ResponseBody
    ShopSettings updatePurchaseOrder(@RequestBody ShopSettings shopSettings) {
        shopSettingsRepository.updateShopSettings(shopSettings);
        return shopSettings;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    ShopSettings updateShopSettings() {
        return shopSettingsRepository.getShopSettings();
    }

}
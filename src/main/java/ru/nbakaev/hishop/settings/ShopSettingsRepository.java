package ru.nbakaev.hishop.settings;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface ShopSettingsRepository {

    /**
     * If we have not {@link ShopSettings} in database - create it
     * @return
     */
    ShopSettings initShopSettings();

    /**
     * Get shop settings
     * @return
     */
    ShopSettings getShopSettings();

    /**
     * Update shop settings
     * @param shopSettings
     * @return
     */
    ShopSettings updateShopSettings(ShopSettings shopSettings);

}

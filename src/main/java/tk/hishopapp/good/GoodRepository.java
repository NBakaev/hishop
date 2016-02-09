package tk.hishopapp.good;

import java.util.List;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface GoodRepository {
    Good createGood(Good good);

    List<Good> getAllGoods();

    Good updateGood(Good good);

    void deleteGoodById(String id);

    List<Good> getAllGoodsOnIndexPage();
}

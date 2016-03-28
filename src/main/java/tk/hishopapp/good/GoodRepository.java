package tk.hishopapp.good;

import tk.hishopapp.dto.GoodEntityDto;
import tk.hishopapp.dto.result.GoodDtoResult;

import java.util.List;

/**
 * Interface that specifies a basic set of Good operations in e-shop. Implemented by {@link GoodRepositoryImpl}.
 * <p>
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface GoodRepository {

    /**
     * Create new good
     *
     * @param good
     * @return
     */
    Good createGood(Good good);

    /**
     * Get all gods
     *
     * @return
     */
    List<Good> getAllGoods();

    /**
     * Update existing good
     *
     * @param good
     * @return
     */
    Good updateGood(Good good);

    /**
     * Delete existing good by id
     *
     * @param id
     */
    void deleteGoodById(String id);

    /**
     * Get all goods which should be shown in UI (html) in main(root) page
     *
     * @return
     */
    List<Good> getAllGoodsOnIndexPage();

    /**
     * Get good by id
     *
     * @param id
     * @return
     */
    Good getGoodById (String id);

    /**
     * Get goods by dto criteria
     *
     * @param goodEntityDto
     * @return
     */
    GoodDtoResult getGoodsByDto(GoodEntityDto goodEntityDto);

}

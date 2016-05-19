package ru.nbakaev.hishop.good;

import ru.nbakaev.hishop.entity.filters.requestdto.GoodFilterRequestDto;
import ru.nbakaev.hishop.entity.filters.responsedto.GoodResultResponseDto;

import java.util.List;

/**
 * Interface that specifies a basic set of Good operations in e-shop. Implemented by {@link GoodRepositoryImpl}.
 * <p>
 * Created by Nikita Bakaev, ya@nbakaev.ru on 2/9/2016.
 * All Rights Reserved
 */
public interface GoodRepository {

    List<Good> createGoods(List<Good> goods);

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
     * Get goods by requestdto criteria
     *
     * @param goodEntityDto
     * @return
     */
    GoodResultResponseDto getGoodsByDto(GoodFilterRequestDto goodEntityDto);

}

package com.ecommerce.service.priceTypeService;

import java.util.List;
import java.util.Optional;

public interface PriceTypeService<PriceType>{
    List<PriceType> list();
    void create(PriceType priceType);
    PriceType get(int id);
    void update(PriceType priceType, int id);
    void delete(int id);
}

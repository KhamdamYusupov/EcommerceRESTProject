package com.ecommerce.service.priceTypeService;

import com.ecommerce.model.PriceType;

import java.util.List;
import java.util.Optional;

public interface PriceTypeService{
    List<PriceType> list();
    void create(PriceType priceType);
    PriceType get(int id);
    void update(PriceType priceType, int id);
    void delete(int id);
}

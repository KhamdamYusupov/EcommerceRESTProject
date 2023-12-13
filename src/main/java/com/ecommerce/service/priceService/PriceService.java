package com.ecommerce.service.priceService;

import com.ecommerce.model.Price;

import java.util.List;
import java.util.Optional;

public interface PriceService{
    List<Price> list();
    void create(Price prices);
    Price get(int id);
    void update(Price prices, int id);
    void delete(int id);

}

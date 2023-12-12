package com.ecommerce.service.priceService;

import java.util.List;
import java.util.Optional;

public interface PriceService<Price>{
    List<Price> list();
    void create(Price prices);
    Price get(int id);
    void update(Price prices, int id);
    void delete(int id);

}

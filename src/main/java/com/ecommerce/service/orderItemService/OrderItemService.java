package com.ecommerce.service.orderItemService;

import com.ecommerce.model.OrderItem;
import com.ecommerce.model.OrderItemView;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> list();
    List<OrderItemView>listForUI();
    void create(OrderItem orderItem);
    OrderItem get(int id);
    void update(OrderItem orderItem, int id);
    void delete(int id);
}

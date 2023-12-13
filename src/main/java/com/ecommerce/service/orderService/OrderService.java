package com.ecommerce.service.orderService;

import com.ecommerce.model.Order;

import java.util.List;

public interface OrderService{
List<Order>list();
void create(Order order);
Order get(int id);
void update(Order order, int id);
void delete(int id);
}

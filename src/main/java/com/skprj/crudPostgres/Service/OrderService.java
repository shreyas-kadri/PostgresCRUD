package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.Entities.Order;
import com.skprj.crudPostgres.Model.OrderRequestDTO;
import com.skprj.crudPostgres.Repository.OrderRepository;
import com.skprj.crudPostgres.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public void addOrder(OrderRequestDTO orderRequestDTO)
    {

    }
}

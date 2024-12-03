package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.Entities.Orders;
import com.skprj.crudPostgres.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderProductService orderProductService;

    private final CustomerService customerService;

    public void placeOrder(OrderRequestDTO orderRequestDTO)
    {
        Optional<Customers> customer=customerService.getCustomerById(orderRequestDTO.getCustomer_id());
        Orders order=new Orders();
        order.setOrderDate(LocalDate.now());
        if(customer.isPresent())
        {
            order.setCustomer(customer.get());
        }
        else
        {
            throw new IllegalArgumentException("Customer not found");
        }
        orderRepository.save(order);
        orderProductService.recordOrder(order,orderRequestDTO);
    }

    public List<Orders> getAllOrders()
    {
        return orderRepository.findAll();
    }

    public Optional<Orders> getOrderDetailsById(int id)
    {
        return orderRepository.findById(id);
    }
}

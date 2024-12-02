package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderProductDTO;
import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.Entities.Customer;
import com.skprj.crudPostgres.Entities.Order;
import com.skprj.crudPostgres.Entities.OrderProduct;
import com.skprj.crudPostgres.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private OrderProductService orderProductService;

    private CustomerService customerService;

    private ProductService productService;

    public void placeOrder(OrderRequestDTO orderRequestDTO)
    {
        Optional<Customer> customer=customerService.getCustomerById(orderRequestDTO.getCustomer_id());
        Order order=new Order();
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

}

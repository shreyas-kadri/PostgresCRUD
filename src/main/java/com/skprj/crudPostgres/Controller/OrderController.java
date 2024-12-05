package com.skprj.crudPostgres.Controller;

import com.skprj.crudPostgres.Entities.Customer;
import com.skprj.crudPostgres.Model.OrderRequestDTO;
import com.skprj.crudPostgres.Model.ResponseDTO;
import com.skprj.crudPostgres.Service.CustomerService;
import com.skprj.crudPostgres.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    private CustomerService customerService;

    @PostMapping("/addOrder")
    public ResponseDTO<OrderRequestDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO)
    {
        Optional<Customer> customer=customerService.getCustomerById(orderRequestDTO.getCustomer_id());
        if(customer.isPresent())
        {
            orderService.addOrder(orderRequestDTO);
        }

    }

}

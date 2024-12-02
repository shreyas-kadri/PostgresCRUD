package com.skprj.crudPostgres.Controller;

import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.DTO.ResponseDTO;
import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.Service.CustomerService;
import com.skprj.crudPostgres.Service.OrderService;
import com.skprj.crudPostgres.Service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;

    private final OrderService orderService;

    private final ProductService productService;

    @Transactional
    @PostMapping("/placeOrder")
    public ResponseDTO<OrderRequestDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO)
    {
        Optional<Customers> customer=customerService.getCustomerById(orderRequestDTO.getCustomer_id());
        if(customer.isPresent())
        {
            if(productService.isInStock(orderRequestDTO))
            {
                orderService.placeOrder(orderRequestDTO);
                return new ResponseDTO<>(true,"order placed successfully",LocalDateTime.now(),orderRequestDTO);
            }
            return new ResponseDTO<>(false,"product mentioned not in stock",LocalDateTime.now(),orderRequestDTO);
        }

        return new ResponseDTO<>(false,"Customer not found,cannot place order", LocalDateTime.now(),orderRequestDTO);

    }

}

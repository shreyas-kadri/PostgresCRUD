package com.skprj.crudPostgres.Controller;

import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.DTO.ResponseDTO;
import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.Entities.Orders;
import com.skprj.crudPostgres.Service.CustomerService;
import com.skprj.crudPostgres.Service.OrderService;
import com.skprj.crudPostgres.Service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
            return new ResponseDTO<>(false,"Not enough stock to place order",LocalDateTime.now(),orderRequestDTO);
        }

        return new ResponseDTO<>(false,"Customer not found,cannot place order", LocalDateTime.now(),orderRequestDTO);

    }

    @GetMapping("/getAllOrders")
    public ResponseDTO<List<Orders>> getAllOrders()
    {
        List<Orders> orders=orderService.getAllOrders();
        return new ResponseDTO<>(true,"All orders fetched successfully",LocalDateTime.now(),orders);
    }

    @GetMapping("getOrderDetailsByCustomerId/{id}")
    public ResponseDTO<Optional<Orders>> getOrderDetailsByCustomerId(@PathVariable int id)
    {
        Optional<Customers> customer=customerService.getCustomerById(id);
        if(customer.isPresent())
        {
            Optional<Orders> order = orderService.getOrderDetailsById(id);
            if (order.isPresent())
                return new ResponseDTO<>(true, "Customer order details fetched successfully", LocalDateTime.now(), order);
            else
                return new ResponseDTO<>(false,"Customer has no orders placed",LocalDateTime.now(),null);
        }
        return new ResponseDTO<>(false,"Customer not found",LocalDateTime.now(),null);
    }
}

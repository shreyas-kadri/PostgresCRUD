package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.Entities.Orders;
import com.skprj.crudPostgres.Repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderProductService orderProductService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private OrderService orderService;

    private OrderRequestDTO orderRequestDTO;
    private Customers customer;
    private Orders order;

    @BeforeEach
    void setUp() {
        customer = new Customers();
        customer.setCustomer_id(1);
        customer.setCustomer_name("John Doe");

        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setCustomer_id(1);

        order = new Orders();
        order.setOrder_id(100);
        order.setOrderDate(LocalDate.now());
        order.setCustomer(customer);
    }

    @Test
    void testPlaceOrder_Success() {
        when(customerService.getCustomerById(1)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        orderService.placeOrder(orderRequestDTO);

        verify(orderRepository, times(1)).save(any(Orders.class));
        verify(orderProductService, times(1)).recordOrder(any(Orders.class), eq(orderRequestDTO));
    }

    @Test
    void testPlaceOrder_CustomerNotFound() {
        when(customerService.getCustomerById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.placeOrder(orderRequestDTO));

        assertEquals("Customer not found", exception.getMessage());
        verify(orderRepository, never()).save(any(Orders.class));
        verify(orderProductService, never()).recordOrder(any(Orders.class), any());
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Orders> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderDetailsById_Found() {
        when(orderRepository.findById(100)).thenReturn(Optional.of(order));

        Optional<Orders> foundOrder = orderService.getOrderDetailsById(100);

        assertTrue(foundOrder.isPresent());
        assertEquals(100, foundOrder.get().getOrder_id());
        verify(orderRepository, times(1)).findById(100);
    }

    @Test
    void testGetOrderDetailsById_NotFound() {
        when(orderRepository.findById(100)).thenReturn(Optional.empty());

        Optional<Orders> foundOrder = orderService.getOrderDetailsById(100);

        assertFalse(foundOrder.isPresent());
        verify(orderRepository, times(1)).findById(100);
    }
}

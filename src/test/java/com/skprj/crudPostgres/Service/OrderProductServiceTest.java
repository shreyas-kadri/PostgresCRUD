package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderProductDTO;
import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.Entities.OrderProduct;
import com.skprj.crudPostgres.Entities.Orders;
import com.skprj.crudPostgres.Entities.Product;
import com.skprj.crudPostgres.Repository.OrderProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderProductServiceTest {

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderProductService orderProductService;

    private Orders order;
    private OrderRequestDTO orderRequestDTO;
    private OrderProductDTO orderProductDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        order = new Orders();
        order.setOrder_id(1);

        product = new Product();
        product.setProduct_id(100);
        product.setProduct_name("Test Product");

        orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductId(100);
        orderProductDTO.setQuantity(2);

        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setProducts(Collections.singletonList(orderProductDTO));
    }

    @Test
    void testRecordOrder() {
        when(productService.getProductById(100)).thenReturn(Optional.of(product));

        orderProductService.recordOrder(order, orderRequestDTO);

        verify(productService, times(1)).reduceStock(100, 2);
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
    }

    @Test
    void testRecordOrder_ProductNotFound() {
        when(productService.getProductById(100)).thenReturn(Optional.empty());

        try {
            orderProductService.recordOrder(order, orderRequestDTO);
        } catch (IllegalArgumentException e) {
            assertEquals("Product not Found", e.getMessage());
        }

        verify(orderProductRepository, never()).save(any(OrderProduct.class));
    }

    @Test
    void testGetTotalBill() {
        when(orderProductRepository.getTotalBill(1, 1)).thenReturn(500.0);

        Double totalBill = orderProductService.getTotalBill(1, 1);

        assertEquals(500.0, totalBill);
        verify(orderProductRepository, times(1)).getTotalBill(1, 1);
    }
}

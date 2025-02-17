package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderProductDTO;
import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.DTO.ProductDTO;
import com.skprj.crudPostgres.Entities.Product;
import com.skprj.crudPostgres.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProduct_id(1);
        product.setProduct_name("Laptop");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setStock(10);

        productDTO = new ProductDTO();
        productDTO.setName("Laptop");
        productDTO.setPrice(BigDecimal.valueOf(1000));
        productDTO.setStock(10);
    }

    @Test
    void testAddProduct() {
        productService.addproduct(productDTO);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<Product> products = productService.getAllProducts();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productService.getProductById(1);
        assertTrue(foundProduct.isPresent());
        assertEquals("Laptop", foundProduct.get().getProduct_name());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());
        Optional<Product> foundProduct = productService.getProductById(2);
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testChangePrice() {
        doNothing().when(productRepository).changePrice(1, BigDecimal.valueOf(1200));

        productService.changePrice(Optional.of(product), BigDecimal.valueOf(1200));

        verify(productRepository, times(1)).changePrice(1, BigDecimal.valueOf(1200));
    }


    @Test
    void testIsInStock_True() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductId(1);
        orderProductDTO.setQuantity(5);
        orderRequestDTO.setProducts(List.of(orderProductDTO));

        when(productRepository.checkStock(1, 5)).thenReturn(true);

        assertTrue(productService.isInStock(orderRequestDTO));
    }

    @Test
    void testIsInStock_False() {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        OrderProductDTO orderProductDTO = new OrderProductDTO();
        orderProductDTO.setProductId(1);
        orderProductDTO.setQuantity(15);
        orderRequestDTO.setProducts(List.of(orderProductDTO));

        when(productRepository.checkStock(1, 15)).thenReturn(false);

        assertFalse(productService.isInStock(orderRequestDTO));
    }

    @Test
    void testReduceStock_Success() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.reduceStock(1, 3);
        verify(productRepository, times(1)).changeStock(1, 7);
    }

    @Test
    void testReduceStock_ProductNotFound() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> productService.reduceStock(2, 3));
    }

    @Test
    void testDeleteById() {
        productService.deleteById(1);
        verify(productRepository, times(1)).deleteById(1);
    }
}

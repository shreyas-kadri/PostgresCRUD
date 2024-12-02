package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderProductDTO;
import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.Entities.Orders;
import com.skprj.crudPostgres.Entities.OrderProduct;
import com.skprj.crudPostgres.Repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;

    private final ProductService productService;

    public void recordOrder(Orders order, OrderRequestDTO orderRequestDTO)
    {
        for(OrderProductDTO orderProductDTO : orderRequestDTO.getProducts())
        {
            productService.reduceStock(orderProductDTO.getProductId(),orderProductDTO.getQuantity());
            OrderProduct orderProduct=new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(productService.getProductById(orderProductDTO.getProductId())
                    .orElseThrow(()-> new IllegalArgumentException("Product not Found")));
            orderProduct.setQuantity(orderProduct.getQuantity());
            orderProductRepository.save(orderProduct);
        }
    }
}

package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.DTO.OrderProductDTO;
import com.skprj.crudPostgres.DTO.OrderRequestDTO;
import com.skprj.crudPostgres.DTO.ProductDTO;
import com.skprj.crudPostgres.Entities.Product;
import com.skprj.crudPostgres.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    public ProductRepository productRepository;

    public void addproduct(ProductDTO productDTO)
    {
        Product product=new Product();
        product.setProduct_name(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        productRepository.save(product);
    }

    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id)
    {
        return productRepository.findById(id);
    }

    public void changePrice(Optional<Product> product, BigDecimal price)
    {
        productRepository.changePrice(product.get().getProduct_id(),price);
    }

    public Boolean isInStock(OrderRequestDTO orderRequestDTO)
    {
        Boolean isInStock=true;
        for (OrderProductDTO orderProductDTO : orderRequestDTO.getProducts())
        {
            isInStock=productRepository.checkStock(orderProductDTO.getProductId(),orderProductDTO.getQuantity());
            if(!isInStock)
                return isInStock;
        }
        return true;
    }

    public void reduceStock(int productId, int quantity)
    {
        Optional<Product> product=productRepository.findById(productId);
        if(product.isPresent())
        {
            int newQuantity=product.get().getStock();
            productRepository.changeStock(productId,newQuantity);
        }
        else
        {
            throw new IllegalArgumentException("product not found");
        }
    }

    public void deleteById(int id)
    {
        productRepository.deleteById(id);
    }
}

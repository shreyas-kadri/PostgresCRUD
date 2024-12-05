package com.skprj.crudPostgres.Controller;

import com.skprj.crudPostgres.DTO.ProductDTO;
import com.skprj.crudPostgres.DTO.ResponseDTO;
import com.skprj.crudPostgres.Entities.Product;
import com.skprj.crudPostgres.Service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Transactional
    @PostMapping("/addProduct")
    public ResponseDTO<ProductDTO> addProduct(@RequestBody ProductDTO productDTO)
    {
        productService.addproduct(productDTO);
        return new ResponseDTO<>(true,"added product successfully", LocalDateTime.now(),productDTO);
    }

    @GetMapping("/getAllProducts")
    public ResponseDTO<List<Product>> getAllProducts()
    {
        List<Product> products=productService.getAllProducts();
        return new ResponseDTO<>(true,"all products fetched successfully",LocalDateTime.now(),products);
    }

    @GetMapping("/getProductById/{id}")
    public ResponseDTO<Optional<Product>> getProductById(@PathVariable int id)
    {
        Optional<Product> product=productService.getProductById(id);
        if(product.isPresent())
        {
            return new ResponseDTO<>(true,"product fetched successfully",LocalDateTime.now(),product);
        }
        else
        {
            return new ResponseDTO<>(true,"product not found",LocalDateTime.now(),null);
        }
    }

    @Transactional
    @PutMapping("/changePrice/{id}")
    public ResponseDTO<Optional<Product>> changePrice(@PathVariable int id,@RequestParam("price") BigDecimal price)
    {
        Optional<Product> product=productService.getProductById(id);
        if(product.isPresent())
        {
            productService.changePrice(product,price);
            return new ResponseDTO<>(true,"price changed successfully",LocalDateTime.now(),null);
        }
        else
        {
            return new ResponseDTO<>(false,"product does not exist",LocalDateTime.now(),null);
        }
    }

    @Transactional
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseDTO<String> deleteProduct(@PathVariable int id)
    {
        Optional<Product> product=productService.getProductById(id);
        if(product.isPresent())
        {
            productService.deleteById(id);
            return new ResponseDTO<>(true,"product deleted",LocalDateTime.now(),null);
        }
        else
        {
            return new ResponseDTO<>(false,"product not found",LocalDateTime.now(),null);
        }
    }

}

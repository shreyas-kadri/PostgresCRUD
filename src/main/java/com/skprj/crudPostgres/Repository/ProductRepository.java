package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Modifying
    @Query(value="update product set price=?2 where product_id=?1",nativeQuery = true)
    void changePrice(int productId, BigDecimal price);

    @Query(value="select case when stock - ?2  >= 0 then true else false end as in_stock from product where product_id =?1",nativeQuery = true)
    Boolean checkStock(int productId,int quantity);

    @Modifying
    @Query(value="update product set stock=?2 where product_id=?1",nativeQuery = true)
    void changeStock(int productId, int newQuantity);
}

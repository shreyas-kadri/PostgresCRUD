package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {

    @Query(value = """
            SELECT SUM(op.quantity * p.price)
            FROM order_product op 
            JOIN product p ON op.product_id = p.product_id
            JOIN orders o ON o.order_id = op.order_id
            WHERE o.customer_id = ?1 AND o.order_id = ?2
            """, nativeQuery = true)
    Double getTotalBill(int custId, int orderId);
}

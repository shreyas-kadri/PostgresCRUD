package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {
}

package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.OrderProduct;
import com.skprj.crudPostgres.Model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}

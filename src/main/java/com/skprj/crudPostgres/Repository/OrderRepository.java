package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Integer> {

}

package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers,Integer> {
}

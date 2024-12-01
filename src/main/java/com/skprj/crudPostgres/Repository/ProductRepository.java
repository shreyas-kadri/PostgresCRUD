package com.skprj.crudPostgres.Repository;

import com.skprj.crudPostgres.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}

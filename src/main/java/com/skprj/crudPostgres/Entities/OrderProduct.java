package com.skprj.crudPostgres.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderProduct {

    @EmbeddedId
    private OrderProductId OrderProduct_id = new OrderProductId();

    @ManyToOne
    @MapsId("order_id")
    private Order order;

    @ManyToOne
    @MapsId("product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity;
}

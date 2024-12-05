package com.skprj.crudPostgres.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private int customer_id;
    private LocalDate orderDate;
    private List<OrderProductDTO> products;

}

package com.skprj.crudPostgres.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private int customer_id;

    private List<OrderProductDTO> products;


}

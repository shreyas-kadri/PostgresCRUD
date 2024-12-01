package com.skprj.crudPostgres.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    public boolean status;

    public String message;

    public LocalDateTime localDateTime;

    public T data;
}

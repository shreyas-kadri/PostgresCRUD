package com.skprj.crudPostgres.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    public boolean status;

    public String message;

    public LocalDateTime localDateTime;

    public T data;
}

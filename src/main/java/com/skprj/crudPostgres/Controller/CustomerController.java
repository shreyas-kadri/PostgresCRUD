package com.skprj.crudPostgres.Controller;

import com.skprj.crudPostgres.DTO.CustomerDTO;
import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.DTO.ResponseDTO;
import com.skprj.crudPostgres.Service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Transactional
    @PostMapping("/createCustomer")
    public ResponseDTO<Customers> createCustomer(@RequestBody CustomerDTO customerDTO)
    {
        Customers customer=new Customers();
        customer.setCustomer_name(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customerService.createCustomer(customer);
        return new ResponseDTO<>(true,"Customer details saved successfully", LocalDateTime.now(),customer);
    }

    @GetMapping("/getAllCustomers")
    public ResponseDTO<List<Customers>> getAllCustomer()
    {
        List<Customers> customers=customerService.getAllCustomers();
        return new ResponseDTO<>(true,"Customer details fetched successfully",LocalDateTime.now(),customers);
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseDTO<Customers> getCustomerById(@PathVariable int id)
    {
        Optional<Customers> customer = customerService.getCustomerById(id);

        if (customer.isPresent())
        {
            return new ResponseDTO<>(true, "Customer found",LocalDateTime.now(),customer.get());
        }
        else
        {
            return new ResponseDTO<>(false, "Customer not found",LocalDateTime.now(), null);
        }
    }

    @Transactional
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseDTO<String> deleteCustomer(@PathVariable int id)
    {
        try {
            Optional<Customers> optionalCustomer = customerService.getCustomerById(id);

            if (optionalCustomer.isPresent()) {
                // If the customer exists, delete their order (if any) and the customer
                customerService.deleteCustomerById(id);

                return new ResponseDTO<>(true, "Customer and associated order deleted successfully",LocalDateTime.now(),null);
            } else {
                // If the customer doesn't exist
                return new ResponseDTO<>(false, "Customer not found",LocalDateTime.now(),null);
            }
        } catch (Exception e) {
            // In case of an error during deletion
            return new ResponseDTO<>(false, "Error deleting customer: " + e.getMessage(), LocalDateTime.now(),null);
        }
    }
}

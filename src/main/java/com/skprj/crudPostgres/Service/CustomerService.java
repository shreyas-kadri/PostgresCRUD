package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.Entities.Customers;
import com.skprj.crudPostgres.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void createCustomer(Customers customer)
    {
        customerRepository.save(customer);
    }

    public List<Customers> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Optional<Customers> getCustomerById(int id)
    {
        return customerRepository.findById(id);
    }

    public void deleteCustomerById(int id)
    {
        customerRepository.deleteById(id);
    }
}

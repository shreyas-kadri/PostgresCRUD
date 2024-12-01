package com.skprj.crudPostgres.Service;

import com.skprj.crudPostgres.Entities.Customer;
import com.skprj.crudPostgres.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    private OrderService orderService;

    public void save(Customer customer)
    {
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(int id)
    {
        return customerRepository.findById(id);
    }

    public void deleteCustomerById(int id)
    {
        customerRepository.deleteById(id);
    }
}

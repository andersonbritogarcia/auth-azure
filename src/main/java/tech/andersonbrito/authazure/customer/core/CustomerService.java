package tech.andersonbrito.authazure.customer.core;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import tech.andersonbrito.authazure.customer.persistence.Customer;
import tech.andersonbrito.authazure.customer.persistence.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}

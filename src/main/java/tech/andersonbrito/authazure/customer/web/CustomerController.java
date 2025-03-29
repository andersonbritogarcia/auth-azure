package tech.andersonbrito.authazure.customer.web;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import tech.andersonbrito.authazure.customer.core.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/customers")
    CustomerResponse createCustomer(@RequestBody @Valid CustomerRequest request) {
        return new CustomerResponse(service.saveCustomer(request.toEntity()));
    }

    @GetMapping("/customers")
    List<CustomerResponse> getAllCustomers() {
        return service.getAllCustomers().stream().map(CustomerResponse::new).toList();
    }

    @GetMapping("/customers/{id}")
    CustomerResponse getCustomerById(@PathVariable UUID id) {
        return new CustomerResponse(service.getCustomerById(id));
    }
}

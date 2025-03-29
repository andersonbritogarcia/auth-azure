package tech.andersonbrito.authazure.customer.web;

import tech.andersonbrito.authazure.customer.persistence.Customer;

import java.util.UUID;

public record CustomerResponse(UUID id, String firstName, String lastName, String email) {

    public CustomerResponse(Customer customer) {
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }
}

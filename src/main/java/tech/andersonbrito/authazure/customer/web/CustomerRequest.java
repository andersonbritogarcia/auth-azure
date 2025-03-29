package tech.andersonbrito.authazure.customer.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import tech.andersonbrito.authazure.customer.persistence.Customer;

public record CustomerRequest(@NotBlank String firstName, @NotBlank String lastName, @Email String email) {

    public Customer toEntity() {
        return new Customer(firstName, lastName, email);
    }
}

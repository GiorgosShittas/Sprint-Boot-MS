package com.springboot.accounts.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.accounts.entity.Customer;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);
    // Custom query methods can be defined here if needed
    // For example:
    // List<Customer> findByName(String name);
    // Optional<Customer> findByEmail(String email);
    
    // JpaRepository provides basic CRUD operations out of the box

}

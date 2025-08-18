package com.springboot.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.accounts.entity.Accounts;

public interface AccountRepository extends JpaRepository<Accounts, Long> {

}

package com.springboot.accounts.repository;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.accounts.entity.Accounts;

public interface AccountRepository extends JpaRepository<Accounts, Long> {
    public Optional<Accounts> findByCustomerId(Long customerId);
    @Transactional
    @Modifying
    public void deleteByCustomerId(Long customerId);
}

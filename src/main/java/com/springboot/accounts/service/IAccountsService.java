package com.springboot.accounts.service;

import com.springboot.accounts.dto.CustomerDto;

public interface IAccountsService {
    public void createAccount(CustomerDto customerDto);
    public CustomerDto fetchAccount(String mobileNumber);
    public boolean updateAccount(CustomerDto customerDto);
    public boolean deleteAccount(String mobileNumber);
}

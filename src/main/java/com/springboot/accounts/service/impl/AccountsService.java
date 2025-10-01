package com.springboot.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.springboot.accounts.constants.AccountsConstants;
import com.springboot.accounts.dto.AccountDto;
import com.springboot.accounts.dto.CustomerDto;
import com.springboot.accounts.entity.Accounts;
import com.springboot.accounts.entity.Customer;
import com.springboot.accounts.exceptions.CustomerAlreadyExistsException;
import com.springboot.accounts.exceptions.ResourceNotFoundException;
import com.springboot.accounts.mapper.AccountsMapper;
import com.springboot.accounts.mapper.CustomerMapper;
import com.springboot.accounts.repository.AccountRepository;
import com.springboot.accounts.repository.CustomerRepository;
import com.springboot.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService{
    
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer =CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (existingCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("customer already exists with this mobile number: "+customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer =customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));
    }
    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts account = accountRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto =CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.mapToAccountsDto(account, new AccountDto()));

        return customerDto;

    }
    
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        // TODO Auto-generated method stub
        boolean isUpdated = false;
        if (customerDto.getAccountDto()!= null){
            
            Accounts account= accountRepository.findById(customerDto.getAccountDto().getAccountNumber()).orElseThrow(()-> new ResourceNotFoundException("Account", "AccountNumber", customerDto.getAccountDto().getAccountNumber().toString()));
            Accounts newAccount=AccountsMapper.mapToAccounts(customerDto.getAccountDto(), account);
            System.out.println("New account: "+newAccount);
            accountRepository.save(newAccount);

            Long customerId= account.getCustomerId();
            System.out.println("Customer ID: "+customerId);
            Customer customer= customerRepository.findById(customerId).orElseThrow(()-> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));
            Customer newCustomer= CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(newCustomer);
            isUpdated=true;
            
           
        }
        return isUpdated;
    }
    @Override
    public boolean deleteAccount(String mobileNumber) {
        // TODO Auto-generated method stub
        Customer customer =customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
    
    /* 
        @Override
     public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountsDTO = customerDto.getAccountDto();
        if(accountsDTO != null){
            Accounts accounts = accountRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber",
                            accountsDTO.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDTO, accounts);
            accounts = accountRepository.save(accounts);

            System.out.println("New account: " + accounts);
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
            
        return isUpdated;
    }
        */
    

}

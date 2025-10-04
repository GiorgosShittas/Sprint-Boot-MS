package com.springboot.accounts.controller;

import org.springframework.web.bind.annotation.RestController;

import com.springboot.accounts.constants.AccountsConstants;
import com.springboot.accounts.dto.CustomerDto;
import com.springboot.accounts.dto.ResponseDto;
import com.springboot.accounts.entity.Customer;
import com.springboot.accounts.service.IAccountsService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping(path="/api",produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {
   private IAccountsService accountsService;
   @PostMapping("/create")
   public ResponseEntity<ResponseDto> createAccount(@Valid@RequestBody CustomerDto customersDto) {
        // Logic to create an account would go here
        // For now, we return a dummy response
        accountsService.createAccount(customersDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping("/accounts")
    public ResponseEntity<String> getAccounts() {
        // Logic to retrieve accounts would go here
        // For now, we return a dummy response
        return ResponseEntity.ok("List of accounts");

   }
   @GetMapping("/fetch")
   public ResponseEntity<CustomerDto> fetchAccount(@RequestParam @Pattern(regexp = "($|[0-9]{10})", message = "Account Number must be 10 digits") String mobileNumber) {
    CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
    return ResponseEntity.status(HttpStatus.OK).body(customerDto);
   }
   @PutMapping("/update")
    public ResponseEntity<ResponseDto>updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,
                    AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(
                    AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam @Pattern(regexp = "($|[0-9]{10})", message = "Account Number must be 10 digits") String mobileNumber){
        boolean isDeleted =false;
        isDeleted = accountsService.deleteAccount(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200,
                    AccountsConstants.MESSAGE_200));
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(
                    AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }
   
}

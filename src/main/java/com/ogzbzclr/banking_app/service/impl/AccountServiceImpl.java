package com.ogzbzclr.banking_app.service.impl;

import com.ogzbzclr.banking_app.dto.AccountDto;
import com.ogzbzclr.banking_app.entity.Account;
import com.ogzbzclr.banking_app.exception.AccountException;
import com.ogzbzclr.banking_app.mapper.AccountMapper;
import com.ogzbzclr.banking_app.repository.AccountRepository;
import com.ogzbzclr.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public AccountDto getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);

    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount");
        }
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account -> AccountMapper.mapToAccountDto(account))).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountException("Account does not exist"));
        accountRepository.deleteById(id);
    }


}

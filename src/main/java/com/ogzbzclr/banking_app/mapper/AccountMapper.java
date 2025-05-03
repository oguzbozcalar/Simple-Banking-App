package com.ogzbzclr.banking_app.mapper;

import com.ogzbzclr.banking_app.dto.AccountDto;
import com.ogzbzclr.banking_app.entity.Account;

public class AccountMapper {
    public static AccountDto mapToAccountDto(Account account){

        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );

        return accountDto;

    }

    public static Account mapToAccount(AccountDto accountDto){
        Account account = new Account();
        account.setId(accountDto.id());
        account.setAccountHolderName(accountDto.accountHolderName());
        account.setBalance(accountDto.balance() );
        return account;
    }


}

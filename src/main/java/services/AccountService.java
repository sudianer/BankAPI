package services;

import model.Account;
import repository.AccountRepository;

import java.util.List;

public class AccountService{
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account get(long id){
       return accountRepository.get(id);
    }

    public List<Account> getAll(){
        return accountRepository.getAll();
    }

    public void create(Account account){
        accountRepository.create(account);
    }

    public List<Account> getByUserID(long userID){
        return accountRepository.getByUserId(userID);
    }
}
package services;

import dao.AccountDAO;
import model.Account;

import java.util.List;

public class AccountService{
    AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO, UserService userService) {
        this.accountDAO = accountDAO;
    }

    public Account get(long id){
       return accountDAO.get(id);
    }

    public List<Account> getAll(){
        return accountDAO.getAll();
    }

    public void create(Account account){
        accountDAO.create(account);
    }

    public List<Account> getByUserID(long userID){
        return accountDAO.getByUserID(userID);
    }
}
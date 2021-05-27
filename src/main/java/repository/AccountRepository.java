package repository;

import dao.AccountDAO;
import model.Account;
import java.util.List;

public class AccountRepository
{
    AccountDAO accountDAO;

    public AccountRepository(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAll() { return accountDAO.getAll(); }

    public Account get(long id){
        return accountDAO.get(id);
    }

    public void update(Account account){
        accountDAO.update(account);
    }

    public void delete(long id) {
        accountDAO.delete(id);
    }

    public void create(Account account){
        accountDAO.create(account);
    }

    public List<Account> getByUserId(long userID){
        return accountDAO.getByUserID(userID);
    }
}
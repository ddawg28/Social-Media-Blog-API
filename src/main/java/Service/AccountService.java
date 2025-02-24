package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
    public Account addAccount(Account account) {
        // check for no username or less than 4 password length
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return null;
        }
        // check if account exists
        if (getAccount(account) != null) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }
    public Account getAccount(Account account) {
        return accountDAO.getAccount(account);
    }
}

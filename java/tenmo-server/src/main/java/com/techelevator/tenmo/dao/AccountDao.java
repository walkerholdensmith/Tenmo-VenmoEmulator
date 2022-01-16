package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    public BigDecimal retrieveBalance(int userId);

    public void updateBalance(BigDecimal newBalance, int userId);

    public BigDecimal getBalance();

    public void setBalance(BigDecimal balance);
    public BigDecimal retrieveBalanceWithAccountId(int account_id);


}

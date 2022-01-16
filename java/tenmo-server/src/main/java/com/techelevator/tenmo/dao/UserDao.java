package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    void setBalance(BigDecimal amount);

    BigDecimal getBalance();

    void updateBalance(BigDecimal amount, int user);

    public void updateBalanceWithAccountId(BigDecimal balance, int user_id);


}

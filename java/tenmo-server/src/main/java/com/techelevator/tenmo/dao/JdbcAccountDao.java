package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {


    private JdbcTemplate jdbcTemplate;
    private BigDecimal balance;
    private int user_id;
    private int account_id;


    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public JdbcAccountDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal retrieveBalance(int userId) {

        String sql = "SELECT balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        BigDecimal balance = null;

        if(results.next()){
            balance = results.getBigDecimal("balance");
        }

        return balance;
    }

    @Override
    public BigDecimal retrieveBalanceWithAccountId(int account_id) {

        String sql = "SELECT balance FROM accounts WHERE account_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, account_id);
        BigDecimal balance = null;

        if(results.next()){
            balance = results.getBigDecimal("balance");
        }

        return balance;
    }

    @Override
    public void updateBalance(BigDecimal newBalance, int user_id){

        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ? ";
        jdbcTemplate.update(sql, newBalance, user_id );

    }




}

package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component; // this was greyed out

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private int transfer_id;
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int getAccountIdFromUserId(int userId){
        String sql = "SELECT account_id FROM accounts WHERE user_id = ?;";
        int accountId = 0;
        accountId = jdbcTemplate.queryForObject(sql, int.class, userId);
        return accountId;
    }


    public int getAccountIdFromUserName(String userName){
        String sql = "SELECT user_id FROM users WHERE username = ?;";
        int userId = 0;
        userId = jdbcTemplate.queryForObject(sql, int.class, userName);
        int accountId = getAccountIdFromUserId(userId);
        return accountId;
    }

    public String getNameFromAccountId(int account_id){
        String sql = "SELECT username\n" +
                "FROM users\n" +
                "JOIN accounts ON users.user_id = accounts.user_id\n" +
                "WHERE account_id = ?;";

        return jdbcTemplate.queryForObject(sql, String.class, account_id);
    }



    @Override
    public void create(Transfer transfer) {
        String sql = "INSERT into transfers (transfer_type_id, transfer_status_id,account_from, account_to, amount) VALUES(?,?,?,?,?);";
        int transferStatus = 2;
        if (transfer.getTransfer_type_id() == 1){
            transferStatus = 1;
        }

        jdbcTemplate.update(sql, transfer.getTransfer_type_id(),
                transferStatus,getAccountIdFromUserId(transfer.getAccountFrom()), getAccountIdFromUserId(transfer.getAccountTo()),
                transfer.getTransferAmount());
    }
    //DOES NOT SHOW PENDING
    @Override
    public List<Transfer> findTransferHistory(String userName) {
        int accountId = getAccountIdFromUserName(userName);
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers WHERE account_from = ? OR account_to = ? AND transfer_status_id <> 1";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        while(results.next()) {


            int transferId = results.getInt("transfer_id");

            int transferType = results.getInt("transfer_type_id");
            int transferStatusId = results.getInt("transfer_status_id");


            int accountFrom = results.getInt("account_from");
            int accountTo = results.getInt("account_to");

            String otherName = "";
            String sendOrReceive = "Send";
            if(accountId == accountFrom){
                otherName = getNameFromAccountId(accountTo);

            } else {
                otherName = getNameFromAccountId(accountFrom);
                //Changed
                sendOrReceive = "Recieve";
            }

            BigDecimal amount = results.getBigDecimal("amount");

            Transfer newTransfer = new Transfer(transferId, transferType,accountFrom,accountTo,amount, otherName, sendOrReceive);
            transfers.add(newTransfer);

        }

        return transfers;
    }

    public void updateTransferStatus(Transfer transfer, int update){
        String sql = "UPDATE transfers SET transfer_status_id = ? WHERE transfer_id = ? ";
        jdbcTemplate.update(sql, update, transfer.getTransfer_id());
    }


    public List<Transfer> findPendingTransfers(String userName) {
        int accountId = getAccountIdFromUserName(userName);
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers WHERE (account_from = ? OR account_to = ?) AND transfer_status_id = 1;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);

        while(results.next()) {

            int transferId = results.getInt("transfer_id");

            int transferType = results.getInt("transfer_type_id");
            int transferStatusId = results.getInt("transfer_status_id");


            int accountFrom = results.getInt("account_from");
            int accountTo = results.getInt("account_to");

            String otherName = "";
            String sendOrReceive = "Send";
            if(accountId == accountFrom){
                otherName = getNameFromAccountId(accountTo);

            } else {
                otherName = getNameFromAccountId(accountFrom);

                sendOrReceive = "Receive";
            }

            BigDecimal amount = results.getBigDecimal("amount");


            Transfer newTransfer = new Transfer(transferId, 1,accountFrom,accountTo,amount, otherName, sendOrReceive);
            newTransfer.setTransfer_status_id(1);
            newTransfer.setTransfer_type_id(1);
            transfers.add(newTransfer);

        }

        return transfers;
    }

    public void acceptTransfer(int transferId){
        String sql = "UPDATE transfers \n" +
                "SET transfer_status_id = 2\n" +
                "WHERE transfer_id = ? ;";
        jdbcTemplate.update(sql, transferId);

    }


    public void rejectTransfer(int transferId){
        String sql = "UPDATE transfers \n" +
                "SET transfer_status_id = 3\n" +
                "WHERE transfer_id = ? ;";
        jdbcTemplate.update(sql, transferId);
    }




}

package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.apache.catalina.authenticator.SingleSignOnListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.Bidi;
import java.util.List;
import java.util.Scanner;

@RestController
@PreAuthorize("isAuthenticated()")
public class AppController {

    @Autowired
    AccountDao accountDao;

    @Autowired
    UserDao userDao;

    @Autowired
    JdbcTransferDao transferDao;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Balance obtainBalance(Principal principal) {

        String name = principal.getName();

        int userId = userDao.findIdByUsername(name);
        BigDecimal balance = accountDao.retrieveBalance(userId);
        Balance balanceObject = new Balance();
        balanceObject.setBalance(balance);
        return balanceObject;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> findAll() {
        List<User> allUsers = userDao.findAll();
        return allUsers;

    }

    //TODO: 1. Should be a POST
    // 2. There should be a body, you need a TransferClass

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public void addTransfer(@RequestBody Transfer transfer) {

        BigDecimal currentBalanceFromUser = accountDao.retrieveBalance(transfer.getAccountFrom());
        BigDecimal xferAmt = transfer.getTransferAmount();
        BigDecimal newFromUserBalance = currentBalanceFromUser.subtract(xferAmt);

        if(transfer.getTransfer_type_id() != 1){
            if (newFromUserBalance.compareTo(new BigDecimal("0")) == 1){
                userDao.updateBalance(newFromUserBalance, transfer.getAccountFrom());
                BigDecimal currentToBalance = accountDao.retrieveBalance(transfer.getAccountTo());
                BigDecimal newToBalance = currentToBalance.add(xferAmt);
                userDao.updateBalance(newToBalance, transfer.getAccountTo());
                transferDao.create(transfer);
            }
        } else {
            transferDao.create(transfer);
        }
    }

    @RequestMapping(path="/acceptTransfer", method = RequestMethod.PUT)
    public void acceptTransfer(@RequestBody Transfer transfer, Principal principal){
        int transferId = transfer.getTransfer_id();



        BigDecimal currentBalanceFromUser = accountDao.retrieveBalance(userDao.findIdByUsername(principal.getName()));
        BigDecimal xferAmt = transfer.getTransferAmount();

        BigDecimal senderBalance = accountDao.retrieveBalance(userDao.findIdByUsername(principal.getName()));
        senderBalance = senderBalance.subtract(xferAmt);

        if (senderBalance.compareTo(new BigDecimal("0")) == 1){
            userDao.updateBalance(senderBalance, userDao.findIdByUsername(principal.getName()));
            BigDecimal requesterBalance = accountDao.retrieveBalanceWithAccountId(transfer.getAccountFrom());
            requesterBalance = requesterBalance.add(xferAmt);
            userDao.updateBalanceWithAccountId(requesterBalance, transfer.getAccountFrom());
            transferDao.acceptTransfer(transferId);
        }




    }

    @RequestMapping(path="/rejectTransfer", method = RequestMethod.PUT)
    public void rejectTransfer(@RequestBody Transfer transfer){
        transferDao.rejectTransfer(transfer.getTransfer_id());
    }


    @RequestMapping(path = "/getTransfers", method = RequestMethod.GET)
    public List<Transfer> seeTransferHistory(Principal principal){
        String userName = principal.getName();
        List<Transfer> transfers = transferDao.findTransferHistory(userName);
        return transfers;
    }

    @RequestMapping(path = "/getPending", method = RequestMethod.GET)
    public List<Transfer> getPendingTransactions(Principal principal){
        String userName = principal.getName();
        List<Transfer> pending = transferDao.findPendingTransfers(userName);
        return pending;
    }


}

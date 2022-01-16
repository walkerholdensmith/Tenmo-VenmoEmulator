package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface TransferDao {

    public void create(Transfer transfer);

    List<Transfer> findTransferHistory(String userName);
    //added
    public int getAccountIdFromUserId(int userId);
    //added
    public int getAccountIdFromUserName(String userName);

    public String getNameFromAccountId(int account_id);

}

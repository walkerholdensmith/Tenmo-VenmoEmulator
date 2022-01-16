package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import junit.framework.TestCase;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;

public class TenmoServiceTest extends TestCase {

    TenmoService testTenmoService = new TenmoService();
    private Transfer testTransfer = new Transfer(2,2,9999,8888,new BigDecimal("100"),"Paul","Send");



    public void testGetAllUsers() {
    }

    public void testAddTransfer() {
    }

    public void testSubtractBalance() {
    }

    public void testAddToBalance() {
    }

    public void testUpdateTransferStatus() {
    }

    public void testGetTransferHistory() {
    }
}
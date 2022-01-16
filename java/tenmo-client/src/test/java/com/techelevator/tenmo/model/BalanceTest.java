package com.techelevator.tenmo.model;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class BalanceTest extends TestCase {


    Balance testBalance = new Balance();


    public void testGetBalance() {
        testBalance.setBalance(new BigDecimal("10"));
        assertEquals(testBalance.getBalance(), new BigDecimal("10"));

    }

    public void testSetBalance() {
        testBalance.setBalance(new BigDecimal("2"));
        assertEquals(testBalance.getBalance(), new BigDecimal("2"));
    }


}
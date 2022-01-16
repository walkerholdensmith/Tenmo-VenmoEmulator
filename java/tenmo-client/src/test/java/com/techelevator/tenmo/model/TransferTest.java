package com.techelevator.tenmo.model;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class TransferTest extends TestCase {

    private Transfer testTransfer = new Transfer(2,2,9999,8888,new BigDecimal("100"),"Paul","Send");

    public void testGetSendOrReceive() {
        String testTransferSendOrReceive = testTransfer.getSendOrReceive();
        assertEquals(testTransferSendOrReceive, "Send");
    }

    public void testSetSendOrReceive() {
        testTransfer.setSendOrReceive("Receive");
        assertEquals("Receive", testTransfer.getSendOrReceive());
    }

    public void testToStringSend() {

    }

    public void testToStringFrom() {
    }

    public void testGetTransfer_id() {
        int transferId = testTransfer.getTransfer_id();
        assertEquals(transferId, 0);
    }

    public void testSetTransfer_id() {
        testTransfer.setTransfer_id(909);
        assertEquals(testTransfer.getTransfer_id(), 909);
    }

    public void testGetTransfer_type_id() {
        assertEquals(testTransfer.getTransfer_type_id(), 2);
    }

    public void testSetTransfer_type_id() {
        testTransfer.setTransfer_type_id(1);
        assertEquals(testTransfer.getTransfer_type_id(), 1);
    }

    public void testGetTransfer_status_id() {
        int actualTransferId = testTransfer.getTransfer_status_id();
        assertEquals(actualTransferId, 2);
    }

    public void testSetTransfer_status_id() {
        testTransfer.setTransfer_status_id(1);
        assertEquals(testTransfer.getTransfer_status_id(), 1);
    }

    public void testGetAccountFrom() {
        int actualAccountFrom = testTransfer.getAccountFrom();
        assertEquals(actualAccountFrom, 9999);
    }

    public void testSetAccountFrom() {
        testTransfer.setAccountFrom(9);
        assertEquals(testTransfer.getAccountFrom(), 9);
    }

    public void testGetAccountToo() {
        int actualAccountToo = testTransfer.getAccountToo();
        assertEquals(actualAccountToo, 8888);
    }

    public void testSetAccountToo() {
        testTransfer.setAccountToo(3);
        assertEquals(testTransfer.getAccountToo(), 3);

    }

    public void testGetOtherName() {
        String otherName = testTransfer.getOtherName();
        assertEquals(otherName, "Paul");
    }

    public void testSetOtherName() {
        testTransfer.setOtherName("Alex");
        assertEquals(testTransfer.getOtherName(), "Alex");
    }

    public void testGetTransferAmount() {
        assertEquals(testTransfer.getTransferAmount(), new BigDecimal("100"));

    }

    public void testSetTransferAmount() {
        testTransfer.setTransferAmount(new BigDecimal("2"));
        assertEquals(testTransfer.getTransferAmount(), new BigDecimal("2"));

    }
}
package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {


    private int transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    private int account_from;
    private int account_to;
    BigDecimal transferAmount;
    String otherName;
    String sendOrReceive;


    public Transfer(int id, int typeId, int account_from, int account_To, BigDecimal transferAmount, String otherName, String sendOrReceive) {

        this.transfer_id = id;
        if (typeId == 1){
            this.transfer_type_id = 1;
            this.transfer_status_id = 1;

        } else {
            this.transfer_type_id = 2;
            this.transfer_status_id =2;
        }

        this.account_from = account_from;
        this.account_to = account_To;
        this.transferAmount = transferAmount;
        this.otherName = otherName;
        this.sendOrReceive = sendOrReceive;
    }



    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_is) {
        this.transfer_status_id = transfer_status_id;
    }

    public int getAccountFrom() {
        return account_from;
    }

    public void setAccountFrom(int accountFrom) {
        this.account_from = accountFrom;
    }

    public int getAccountTo() {
        return this.account_to;
    }

    public void setAccountToo(int accountToo) {
        this.account_to = accountToo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transfer_id=" + transfer_id +
                ", transfer_type_id=" + transfer_type_id +
                ", transfer_status_id=" + transfer_status_id +
                ", account_from=" + account_from +
                ", account_too=" + account_to +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
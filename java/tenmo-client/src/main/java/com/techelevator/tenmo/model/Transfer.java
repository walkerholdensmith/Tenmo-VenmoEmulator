package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transfer_id;
    private int transfer_type_id;
    private int transfer_status_id;
    private int accountFrom;
    private int accountTo;
    private int account_to;
    BigDecimal transferAmount;
    String otherName;
    String sendOrReceive;

    public Transfer( int typeId,int transfer_status_id ,int personOne, int personTwo, BigDecimal transferAmount, String otherName, String sendOrReceive) {

        this.transfer_type_id = typeId;
        this.transfer_status_id = transfer_status_id;
        this.accountFrom = personOne;
        this.accountTo = personTwo;
        this.transferAmount = transferAmount;
        this.otherName = otherName;
        this.sendOrReceive = sendOrReceive;
        this.account_to = personTwo;
    }

    public Transfer(){

    }

    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }


    public String toStringSend() {
        return String.format("%-10d %-20s $ " + transferAmount+ "", transfer_id, "To: " + otherName , transferAmount);
        //return transfer_id + "          To: " +otherName + "          $ " + transferAmount;
    }


    public String toStringFrom() {

        return  String.format("%-10d %-20s $ " + transferAmount+ "", transfer_id, "From: " + otherName, transferAmount);
        //return transfer_id + "          From: " +otherName + "          $ " + transferAmount;
    }


    public String individualToString(String currentUserName){
        String toReturn = "";
        if(this.getSendOrReceive().equals("Send")){
            toReturn = " Id: " + this.transfer_id + "\n" +
                    " From: " + currentUserName + "\n" +
                    " To: " + this.otherName + "\n" +
                    " Type: Send\n" +
                    " Status: Approved\n" +
                    " Amount: $" + this.transferAmount;
        } else if(this.transfer_status_id == 1){
            toReturn = " Id: " + this.transfer_id + "\n" +
                    " From: " + this.otherName + "\n" +
                    " To: " + currentUserName+ "\n" +
                    " Type: Send\n" +
                    " Status: Pending\n" +
                    " Amount: $" + this.transferAmount;
        }

        else {
            toReturn = " Id: " + this.transfer_id + "\n" +
                    " From: " + this.otherName + "\n" +
                    " To: " + currentUserName + "\n" +
                    " Type: Receive\n" +
                    " Status: Approved\n" +
                    " Amount: $" + this.transferAmount;

        }
        return toReturn;
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

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }

    public int getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public int getAccountToo() {
        return accountTo;
    }

    public void setAccountToo(int accountToo) {
        this.accountTo = accountToo;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}

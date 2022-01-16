package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

public class TenmoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;


    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public BigDecimal retrieveBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Balance balance = restTemplate.exchange("http://localhost:8080/balance", HttpMethod.GET, entity, Balance.class).getBody();
        return balance.getBalance();

    }

    public User[] getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        User[] users = restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, entity, User[].class).getBody();
        return users;
    }

    public Transfer addTransfer(Transfer newTransfer) {
        Transfer returnedTransfer = null;
        try {
            restTemplate.exchange("http://localhost:8080/transfer", HttpMethod.POST, makeTransferEntity(newTransfer),Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e){

        }
        return returnedTransfer;

    }


    public void subtractBalance(Transfer transfer){

       restTemplate.exchange("http://localhost:8080/subtractBalance", HttpMethod.PUT, makeTransferEntity(transfer), Void.class);
    }
    public void addToBalance(Transfer transfer){

        restTemplate.exchange("http://localhost:8080/addToBalance", HttpMethod.PUT, makeTransferEntity(transfer), Void.class);
    }


    public void updateTransferStatus(Transfer transfer){
        restTemplate.exchange("http://localhost:8080/updateTransferStatus", HttpMethod.PUT, makeTransferEntity(transfer), Void.class);
    }


    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    public Transfer[] getTransferHistory(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfer[] transfers = restTemplate.exchange("http://localhost:8080/getTransfers", HttpMethod.GET, entity, Transfer[].class).getBody();
        return transfers;

    }

    public Transfer[] getPendingTransfers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        Transfer[] transfers = restTemplate.exchange("http://localhost:8080/getPending", HttpMethod.GET, entity, Transfer[].class).getBody();
        return transfers;

    }

    public void acceptTransfer(Transfer transfer){

        restTemplate.exchange("http://localhost:8080/acceptTransfer", HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class).getBody();

    }

    public void rejectTransfer(Transfer transfer){
        restTemplate.exchange("http://localhost:8080/rejectTransfer", HttpMethod.PUT, makeTransferEntity(transfer), Transfer.class).getBody();

    }


}

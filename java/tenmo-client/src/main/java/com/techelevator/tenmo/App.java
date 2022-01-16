package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.view.ConsoleService;
import org.openqa.selenium.json.JsonOutput;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private TenmoService tenmoService;

    public static void main(String[] args) {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
        app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
        this.console = console;
        this.authenticationService = authenticationService;
        this.tenmoService = new TenmoService();
    }

    public void run() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                viewCurrentBalance();
            } else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                viewTransferHistory();
            } else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
            } else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
                sendBucks();
            } else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
            } else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        System.out.println("Your current account balance is: $"+tenmoService.retrieveBalance());
    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        //System.out.println(currentUser.getUser());
        User user = currentUser.getUser();
        Transfer [] transfers = tenmoService.getTransferHistory();

        if(transfers.length > 0){
            System.out.println("-------------------------------------------\n" +
                    "Transfers\n" +
                    "ID          From/To                 Amount\n" +
                    "-------------------------------------------");
            for (Transfer transfer: transfers){
                if (transfer.getSendOrReceive().equals("Send")){
                    System.out.println(transfer.toStringSend());
                } else {
                    System.out.println(transfer.toStringFrom());
                }
            }
            String currentUserName = currentUser.getUser().getUsername();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which transfer would you like to view the details of?:");
            try {
                int transferToView = Integer.parseInt(scanner.nextLine());
                System.out.println("--------------------------------------------\n" +
                        "Transfer Details\n" +
                        "--------------------------------------------");
                for (Transfer transfer: transfers){
                    if (transfer.getTransfer_id() == transferToView){
                        System.out.println(transfer.individualToString(currentUserName));
                    }
                }
            } catch (NumberFormatException e){
                System.out.println("Invalid Entry");
            }
        }


    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub
        User user = currentUser.getUser();
        String currentUserName = user.getUsername();
        Transfer [] pending = tenmoService.getPendingTransfers();

        if(pending.length > 0){

            System.out.println("-------------------------------------------\n" +
                    "Pending Transfers\n" +
                    "ID          To                     Amount\n" +
                    "-------------------------------------------");
            for(Transfer transfer : pending){
                if(transfer.getSendOrReceive().equals("Receive")){
                    System.out.println(transfer.toStringSend());
                }
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter transfer ID to approve/reject (0 to cancel): ");
            try {
                int transferToView = Integer.parseInt(scanner.nextLine());
                System.out.println("--------------------------------------------\n" +
                        "Transfer Details\n" +
                        "--------------------------------------------");
                for (Transfer transfer: pending){
                    if (transfer.getTransfer_id() == transferToView ){
                        System.out.println(transfer.individualToString(currentUserName));
                        System.out.println("\n1: Approve\n" +
                                "2: Reject\n" +
                                "0: Don't approve or reject\n" +
                                "---------\n" +
                                "Please choose an option:");
                        String answer = scanner.nextLine();
                        if (answer.equals("1")){
                            tenmoService.acceptTransfer(transfer);
                        } else if (answer.equals("2")){
                            tenmoService.rejectTransfer(transfer);
                        }
                    }

                }

            } catch (NumberFormatException e){
                System.out.println("Invalid Entry");
            }

        }


    }

    //TODO 1. Get a scaner going
    // 2. display the list of people you can send money to
    // 3. have the user type in the id of who they want
    // to send it to and the amount.
    // 4. Take those pieces of data and make a Transfer
    // object

    private void sendBucks() {
        // TODO Auto-generated method stub
        User[] allUsers = tenmoService.getAllUsers();
        System.out.println("-------------------------------------------\n" +
                "Users\n" +
                "ID          Name\n" +
                "-------------------------------------------");
        for (User user : allUsers) {
            System.out.println(user);
        }

        Transfer newTransfer = createTransferObject(2,2);
        tenmoService.addTransfer(newTransfer);

    }

    private void requestBucks() {
        // TODO Auto-generated method stub
        User[] allUsers = tenmoService.getAllUsers();
        System.out.println("-------------------------------------------\n" +
                "Users\n" +
                "ID          Name\n" +
                "-------------------------------------------");
        for (User user : allUsers) {
            System.out.println(user);
        }
        Transfer newTransfer = createTransferObject(1,1);
        tenmoService.addTransfer(newTransfer);
    }

    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while (!isAuthenticated()) {
            String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                exitProgram();
            }
        }
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch (AuthenticationServiceException e) {
                System.out.println("REGISTRATION ERROR: " + e.getMessage());
                System.out.println("Please attempt to register again.");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null)
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
                tenmoService.setAuthToken(currentUser.getToken());
            } catch (AuthenticationServiceException e) {
                System.out.println("LOGIN ERROR: " + e.getMessage());
                System.out.println("Please attempt to login again.");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }

    public Transfer createTransferObject(int transferType, int transferStatus) {
        System.out.println("Enter ID of user you are sending to (0 to cancel): ");
        Scanner scanner = new Scanner(System.in);
        User[] allUsers = tenmoService.getAllUsers();
        Transfer newTransfer = null;
        int id2 = 0;
        try {
            id2 = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            System.out.println("Not a numeric user ID.");
        }

        if (id2 != 0){
            boolean isValidUser = false;
            for (User user : allUsers) {
                if (id2 == user.getId()){
                    isValidUser = true;
                }
            }
            if (isValidUser){
                System.out.println("Enter amount: ");
                BigDecimal amount = new BigDecimal("0");
                try {
                   amount = new BigDecimal(scanner.nextLine());
                } catch (NumberFormatException e){
                    System.out.println("Not a valid money amount.");
                }
                newTransfer = new Transfer(transferType, transferStatus,currentUser.getUser().getId(), id2, amount, "", "Send");
                if (transferType == 1){
                    newTransfer.setTransfer_status_id(1);
                }

            } else {
                System.out.println("Not a valid user ID.");
            }
        }
        return newTransfer;

    }
}

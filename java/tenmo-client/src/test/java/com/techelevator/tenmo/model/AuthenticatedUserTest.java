package com.techelevator.tenmo.model;

import io.cucumber.java.bs.A;
import junit.framework.TestCase;

public class AuthenticatedUserTest extends TestCase {

    AuthenticatedUser authenticatedUserTest = new AuthenticatedUser();
    User testUser = new User();


    public void testGetToken() {
        authenticatedUserTest.setToken("test");
        assertEquals(authenticatedUserTest.getToken(), "test");

    }

    public void testSetToken() {
        authenticatedUserTest.setToken("test2");
        assertEquals(authenticatedUserTest.getToken(), "test2");
    }

    public void testGetUser() {
        testUser.setUsername("tester");
        authenticatedUserTest.setUser(testUser);
        assertEquals(testUser.getUsername(), "tester");

    }

    public void testSetUser() {
        testUser.setUsername("tester1");
        authenticatedUserTest.setUser(testUser);
        assertEquals(testUser.getUsername(), "tester1");

    }
}
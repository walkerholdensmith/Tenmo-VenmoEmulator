package com.techelevator.tenmo.model;

import junit.framework.TestCase;

public class UserCredentialsTest extends TestCase {

    UserCredentials userCredentialsTest = new UserCredentials("TEST", "TEST");


    public void testGetUsername() {
        assertEquals("TEST", userCredentialsTest.getUsername());
    }

    public void testSetUsername() {

    }

    public void testSetPassword() {
    }
}
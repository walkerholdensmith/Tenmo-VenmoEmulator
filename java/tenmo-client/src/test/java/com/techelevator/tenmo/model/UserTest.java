package com.techelevator.tenmo.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    User testUser = new User();

    public void testGetId() {

        testUser.setId(1000);
        Integer expected = 1000;
        assertEquals(testUser.getId(), expected);

    }

    public void testSetId() {

        testUser.setId(4);
        Integer expected = 4;
        assertEquals(testUser.getId(), expected);

    }

    public void testGetUsername() {
        testUser.setUsername("walker");
        assertEquals(testUser.getUsername(),"walker");

    }

    public void testSetUsername() {

        testUser.setUsername("bob");
        assertEquals(testUser.getUsername(),"bob");
    }

    public void testTestToString() {

    }
}
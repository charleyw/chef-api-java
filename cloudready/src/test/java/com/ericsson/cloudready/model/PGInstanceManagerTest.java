package com.ericsson.cloudready.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PGInstanceManagerTest {

    @Test
    public void testGetToken() {
        PGInstanceManager pm = new PGInstanceManager();
        String token = pm.getOpToken();
        System.out.println("Token: "+token);
    }

}

package com.ericsson.cloudready.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class PGInstanceManagerTest {

    @Test
    public void testGetToken() {
        PGInstanceManager pm = new PGInstanceManager();
        Instance instance = pm.newInstance("FIRST_TEST", "PG_standalone");
        
    }

}

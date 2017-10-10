package com.gitrekt.resort;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This class is here just to test the basic functionality of JUnit in our 
 * application. It should be removed as we get further into production, but for
 * now, it is a good way to make sure JUnit is working properly.
 */
public class TestGitRekt {
    // TODO: Remove temporary tests, add real tests.
    
    @Test
    public void testJUnitWorking() {
        int i = 1;
        assertNotNull("Something is very, very wrong.", i);
    }
    
    @Ignore
    @Test
    public void testJUnitTestFailure() {
        fail("This test will always fail.");
    }
}
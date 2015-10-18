package net.javapla.chains;

import static net.javapla.chains.Try.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintStream;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;


public class TryRunnableTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        PrintStream print = mock(PrintStream.class);
        System.setOut(print);
    }
    
    // after each method call
    @After
    public void tearDown() throws Exception {
        reset(System.out);
    }
    
    
    @Test
    public void performAndExecute_withTrue() {
        boolean[] called = new boolean[1];
        
        perform(()-> {called[0] = true;}).execute();
        assertTrue(called[0]);
        
        String testify = "testify";
        perform(() -> doSomething(testify)).execute();
        verify(System.out).println(testify);
    }
    
    @Test
    public void performAndExecuteNothing() {
        boolean[] called = new boolean[1];
        
        perform(()-> {}).execute();
        assertFalse(called[0]);
    }
    
    @Test
    public void performAndExecute_withNull() {
        perform(() -> doSomething(null)).execute();
        String s = null;
        verify(System.out).println(s);
    }
    
    @Test
    public void throwAndCatchNullpointer() {
        perform(() -> throwNullpointer())
            .exception((e)->System.out.println(e.getMessage()), NullPointerException.class)
            .execute();
        verify(System.out).println(anyString());
    }
    
    @Test(expected = RuntimeException.class)
    public void throwButNotCatchException() {
        perform(() -> throwNullpointer()).execute();
    }
    
    @Test(expected = RuntimeException.class)
    public void throwAndCatchButIsNotNullpointer() {
        perform(() -> throwException())
            .exception(e -> System.out.println(e.getMessage()), NullPointerException.class)
            .execute();
    }
    
    @Test
    public void throwAndCatchAll() {
        perform(() -> throwException())
            .exception(e -> System.out.println(e.getMessage())) // when nothing else is specified then it is a catch-all
            .execute();
        
        perform(() -> throwNullpointer())
            .exception(e -> System.out.println(e.getMessage()))
            .execute();
    }
    
    @Test
    public void throwNullpointerButLookForException() {
        //Exception is the super class of nullpointer
        perform(() -> throwNullpointer())
            .exception(e -> System.out.println(e.getMessage()), Exception.class)
            .execute();
        
        verify(System.out).println(anyString());
    }
    
    public static void throwNullpointer() throws NullPointerException {
        throw new NullPointerException("nulls everywhere");
    }

    public static void throwException() throws Exception {
        throw new Exception("nothing to see here");
    }

    public static void doSomething(String s) {
        System.out.println(s);
    }

}

package net.javapla.chains;

import static net.javapla.chains.Try.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.PrintStream;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;


public class TrySupplierTest {

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
    public void performAndExecute_withReturn() {
        assertTrue(perform(()-> true).execute().get());
        
        String testify = "testify";
        assertEquals(testify, perform(() -> testify).execute().get());
    }
    
    @Test
    public void performAndExecute_withNull() {
        assertFalse(perform(() -> null).execute().isPresent());
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
        
        verify(System.out, times(2)).println(anyString());
    }
    
    @Test
    public void throwNullpointerButLookForException() {
        //Exception is the super class of nullpointer
        perform(() -> throwNullpointer())
            .exception(e -> System.out.println(e.getMessage()), Exception.class)
            .execute();
        
        verify(System.out).println(anyString());
    }
    
    public static Integer throwNullpointer() throws NullPointerException {
        throw new NullPointerException("nulls everywhere");
    }

    public static Integer throwException() throws Exception {
        throw new Exception("nothing to see here");
    }

    public static String doSomething(String s) {
        System.out.println(s);
        return s;
    }
}

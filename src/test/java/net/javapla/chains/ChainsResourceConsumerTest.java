package net.javapla.chains;

import static net.javapla.chains.Chains.tryWith;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChainsResourceConsumerTest {

    private static Stream stream;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        PrintStream print = mock(PrintStream.class);
        System.setOut(print);
        
        stream = mock(Stream.class);
    }

    @After
    public void tearDown() throws Exception {
        reset(System.out);
        reset(stream);
    }

    @Test
    public void tryWithStream() {
        int[] ints = new int[1];
        IntStream stream = IntStream.range(0, 10);
        tryWith(stream).perform((IntStream a) -> {a.forEach(i -> ints[0]++);}).execute();
        
        assertThat(ints[0], is(10));
    }
    
    @Test
    public void tryWithStreamClosed() throws Exception {
        tryWith(stream).perform((Stream a) -> {}).execute();
        
        verify(stream, times(1)).close();
    }
    
    @Test
    public void expect_RuntimeExceptionConversion_when_tryWithStreamExceptionNotExplicitCaught() throws Exception {
        doThrow(new Exception()).when(stream).close();
        
        try {
            tryWith(stream).perform((Stream a) -> {}).execute();
        } catch(RuntimeException e) {
            verify(stream).close();
        }
    }
    
    @Test
    public void tryWithStreamException() throws Exception {
        doThrow(new Exception()).when(stream).close();
        
        tryWith(stream).perform((Stream a) -> a.doSomething("")).exception((e)->System.out.println(e.getMessage())).execute();
        verify(System.out).println(anyString());
        verify(stream).close();
    }
    
    @Test(expected = RuntimeException.class)
    public void expect_RuntimeExceptionConversion_when_tryWithStreamExceptionNotExplicitCorrect() throws Exception {
        doThrow(new Exception()).when(stream).close();
        
        tryWith(stream).perform((Stream a) -> a.doSomething("")).exception((e)->System.out.println(e.getMessage()), NullPointerException.class).execute();
    }
    
    @Test
    public void catchExceptionFromResourceUsage() {
        doThrow(new NullPointerException("null is for noone")).when(stream).doException();
        
        tryWith(stream).perform((Stream s) -> s.doException()).exception((e) -> {}).execute();
        verify(stream).doException();
    }
    
    @Test
    public void catchExceptionExplicitlyFromResourceUsage() {
        doThrow(new NullPointerException("null is for noone")).when(stream).doException();
        
        tryWith(stream).perform((Stream s) -> s.doException()).exception((e) -> {}, NullPointerException.class).execute();
        verify(stream).doException();
    }
    
    @Test(expected = Exception.class)
    public void wrongExceptionCatch() {
        doThrow(new Exception("muuh")).when(stream).doException();
        
        tryWith(stream).perform((Stream s) -> s.doException()).exception((e) -> {}, NullPointerException.class).execute();
    }
    
    @Test
    public void handlingSubclassException() {
        doThrow(new NullPointerException("null is for noone")).when(stream).doException();
        
        tryWith(stream).perform((Stream s) -> s.doException()).exception((e) -> System.out.println(e.getMessage()), Exception.class).execute();
        verify(stream).doException();
        verify(System.out).println(anyString());
    }

    private static interface Stream extends AutoCloseable {

        IntStream range(int a, int b);
        void doSomething(String s);
        void doException() throws NullPointerException;
    }
}

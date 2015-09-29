package net.javapla.chains;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.IntStream;

import net.javapla.chains.Try;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class TryResourceFunctionTest {

    private static Stream stream;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        stream = mock(Stream.class);
    }

    @After
    public void tearDown() throws Exception {
        reset(stream);
    }

    @Test
    public void tryWithStream() {
        IntStream stream = IntStream.range(0, 10);
        Optional<Long> optional = Try.with(()->stream).perform((IntStream a) -> a.count()).execute();
        
        assertThat(optional.get(), is(10L));
    }
    
    @Test
    public void tryWithStreamClosed() throws Exception {
        Optional<Integer> optional = Try.with(()->stream).perform((Stream a) -> 42).execute();
        
        verify(stream, times(1)).close();
        assertThat(optional.get(), is(42));
    }
    
    @Test
    public void tryWithStreamReturn() {
        when(stream.doSomething(anyString())).thenCallRealMethod();
        Optional<String> optional = Try.with(()->stream).perform((Stream s) -> s.doSomething("cookie")).execute();
        
        assertThat(optional.get(), is("cookie"));
        try {
            verify(stream, times(1)).close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    public void tryWithMultipleStreams_then_perform() throws Exception {
        when(stream.doSomething(anyString())).thenCallRealMethod();
        Stream stream2 = mock(Stream.class);
        when(stream2.doSomething(anyString())).thenCallRealMethod();
        Optional<String> optional = Try.with(() ->stream).with((Stream s) -> stream2).perform((Stream s) -> s.doSomething("cookie")).execute();
        
        verify(stream, times(1)).close();
        verify(stream2, times(1)).doSomething(anyString());
        assertThat(optional.get(), is("cookie"));
        verify(stream2, times(1)).close();
    }
    
    @Test
    public void tryWithMultipleStreams() throws Exception {
        when(stream.doSomething(anyString())).thenCallRealMethod();
        Stream stream2 = mock(Stream.class);
        when(stream2.doSomething(anyString())).thenCallRealMethod();
        Optional<Stream> optional = Try.with(()->stream).with((s) -> stream2).execute();
        
        verify(stream, times(1)).close();

        // the stream should not have been closed if it has not been used
        verify(stream2, times(0)).close();
        optional.get().close();
        verify(stream2, times(1)).close();
    }
    
    
    private static class Stream implements AutoCloseable {
        String doSomething(String s) {
            return s;
        }
        
        @Override
        public void close() throws Exception {
        }
    }

}

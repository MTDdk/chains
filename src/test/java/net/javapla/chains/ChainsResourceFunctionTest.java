package net.javapla.chains;

import static net.javapla.chains.Chains.tryWith;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChainsResourceFunctionTest {

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
        Optional<Long> optional = tryWith(stream).perform((IntStream a) -> a.count()).execute();
        
        assertThat(optional.get(), is(10L));
    }
    
    @Test
    public void tryWithStreamClosed() throws Exception {
        Optional<Integer> optional = tryWith(stream).perform((Stream a) -> 42).execute();
        
        verify(stream, times(1)).close();
        assertThat(optional.get(), is(42));
    }
    
    @Test
    public void tryWithStreamReturn() throws Exception {
        when(stream.doSomething(anyString())).thenCallRealMethod();
        Optional<String> optional = tryWith(stream).perform((Stream s) -> s.doSomething("cookie")).execute();
        
        assertThat(optional.get(), is("cookie"));
        verify(stream, times(1)).close();
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

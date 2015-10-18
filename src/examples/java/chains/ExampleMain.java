package chains;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import net.javapla.chains.Try;

public class ExampleMain {

    public static void main(String[] args) {
        Integer result = 
                Try.perform(() -> 42)
                   .perform((Integer i)-> i+10)
                   .execute().get();
        System.out.println(result); // Output: 52
        
        
        result = Try.with(() -> new TestResource())
                    .perform((TestResource stream) -> stream.increment(42) )
                    .execute() // closing the stream(s)
                    .get(); // Optional.get()
        System.out.println(result); // Output: Closed with result: 43
        
        
        Try.with(() -> new TestResource())
           .with((TestResource stream) -> stream.openConnection("testing") ) // using the stream and producing another stream
           .perform((ByteArrayInputStream stream ) -> stream.available())
           .perform((Integer number) -> System.out.print("Number of characters in \"testing\": " + number + " - "))
           .execute() // closing the stream(s)
           ;
        // Output: 
        // Number of characters in "testing": 7 - Closed with result: ByteArrayInputStream closed
    }
    
    private static class TestResource implements AutoCloseable {
        
        public int increment(int value) {
            return ++value;
        }
        
        public ByteArrayInputStream openConnection(String s) {
            return new ByteArrayInputStream(s.getBytes()) {
                @Override
                public void close() throws IOException {
                    super.close();
                    System.out.println("ByteArrayInputStream closed");
                }
            };
        }
        
        @Override
        public void close() throws Exception {
            System.out.print("Closed with result: ");
        }
    }
}

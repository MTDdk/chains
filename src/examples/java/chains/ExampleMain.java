package chains;

import java.sql.Connection;
import java.sql.ResultSet;

import net.javapla.chains.Try;

public class ExampleMain {

    public static void main(String[] args) {
        Try
        .with(() ->(Connection)null)
        .with((Connection conn) -> conn.createStatement().executeQuery(""))
        .perform((ResultSet rs) -> { rs.next();})
        .perform(() -> System.out.println(""))
        .perform(() -> 42);
    
        Try
        .with(() ->(Connection)null)
        .with((Connection conn) -> conn.createStatement().executeQuery(""))
        ;
        
        
        Try
        .with(() ->(Connection)null)
        .with((Connection conn) -> conn.createStatement().executeQuery(""))
        .perform((ResultSet rs) -> rs.next());
        
        Try
        .perform(() -> 42)
        .perform((Integer i)-> i+10)
        .execute();
    }
}

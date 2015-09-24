package net.javapla.chains.executors;

import java.util.Optional;

public interface ExecuteReturn<R> {
    
    public Optional<R> execute() throws RuntimeException;

}

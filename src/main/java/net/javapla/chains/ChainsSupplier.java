package net.javapla.chains;

import java.util.Optional;

import net.javapla.chains.executors.ExecuteReturn;
import net.javapla.chains.function.ThrowingSupplier;

public final class ChainsSupplier<R> extends AbstractChains<ThrowingSupplier<R>, ChainsSupplier<R>> implements ExecuteReturn<R> {
    
    ChainsSupplier(ThrowingSupplier<R> s) {
        super(s);
    }
    
    @Override
    public Optional<R> execute() throws RuntimeException {
        try {
            return Optional.ofNullable(runner.get());
        } catch (Throwable e) {
            handleException(e); // throws, so no actual need for the last line
            return Optional.empty();
        }
    }
}
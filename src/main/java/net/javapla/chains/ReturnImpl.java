package net.javapla.chains;

import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingSupplier;

class ReturnImpl<R, E> extends AbstractReturn<R, E/*NormalReturn<R>*/> {

    protected final ThrowingSupplier<R> runner;
    ReturnImpl(final ThrowingSupplier<R> r, final Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
        runner = r;
    }
    
    @Override
    protected Optional<R> internalExecute() throws RuntimeException {
        try {
            return Optional.ofNullable(runner.get());
        } catch (Throwable e) {
            handleException(e); // throws, so no actual need for the last line
            return Optional.empty();
        }
    }

}

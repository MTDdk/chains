package net.javapla.chains;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingSupplier;

class ReturnImpl<R> extends AbstractReturn<R> {

    ThrowingSupplier<R> runner;
    ReturnImpl(ThrowingSupplier<R> r, Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
        runner = r;
    }
    ReturnImpl(ThrowingSupplier<R> r) {
        this(r, new LinkedList<>());
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

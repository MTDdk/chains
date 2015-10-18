package net.javapla.chains.old;

import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingConsumer;

class InputImpl<P> extends AbstractVoid {
    
    protected P resource;
    protected ThrowingConsumer<P> runner;
    InputImpl(ThrowingConsumer<P> r, P resource, Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
        runner = r;
        this.resource = resource;
    }

    
    @Override
    protected Optional<Void> internalExecute() throws RuntimeException {
        try {
            runner.accept(resource);
        } catch (Throwable e) {
            handleException(e);
        }
        return Optional.empty();
    }
}

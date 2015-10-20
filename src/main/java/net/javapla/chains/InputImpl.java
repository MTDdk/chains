package net.javapla.chains;

import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingConsumer;

class InputImpl<P> extends AbstractVoid {
    
    protected final P resource;
    protected final ThrowingConsumer<P> runner;
    InputImpl(final ThrowingConsumer<P> r, P resource, final Queue<AutoCloseable> closeableStack) {
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

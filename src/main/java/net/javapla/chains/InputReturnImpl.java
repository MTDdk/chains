package net.javapla.chains;

import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingFunction;

class InputReturnImpl<P, R, E> extends AbstractReturn<R, E/*NormalReturn<R>*/> {

    protected final P resource;
    protected final ThrowingFunction<P, R> runner;
    InputReturnImpl(ThrowingFunction<P, R> f, P resource, Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
        runner = f;
        this.resource = resource;
    }
    
    @Override
    protected Optional<R> internalExecute() throws RuntimeException {
        try {
            return Optional.ofNullable(runner.apply(resource));
        } catch (Throwable e) {
            handleException(e); // throws, so no actual need for the last line
            return Optional.empty();
        }
    }

}

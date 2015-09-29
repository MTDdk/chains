package net.javapla.chains;

import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;
import net.javapla.chains.interfaces.Return;
import net.javapla.chains.interfaces.Work;

abstract class AbstractReturn<R> extends AbstractChains<Return<R>, R> implements Return<R> {

    
    protected AbstractReturn(Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
    }
    
    @Override
    public <T> Return<T> perform(ThrowingFunction<R,T> f) {
        R r = internalExecute().get();
        return new InputReturnImpl<>(f, r, closeableStack);
    }

    @Override
    public Work perform(ThrowingConsumer<R> c) {
        R r = internalExecute().get();
        return new InputImpl<>(c, r, closeableStack);
    }

    @Override
    public Optional<R> execute() throws RuntimeException {
        try { return internalExecute(); }
        finally { closeAll();}
    }

}

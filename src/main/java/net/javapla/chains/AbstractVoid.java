package net.javapla.chains;

import java.util.Queue;

import net.javapla.chains.function.ThrowingRunnable;
import net.javapla.chains.function.ThrowingSupplier;
import net.javapla.chains.interfaces.Return;
import net.javapla.chains.interfaces.Work;

abstract class AbstractVoid extends AbstractChains<Work, Void> implements Work {

    protected AbstractVoid(Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
    }
    
    @Override
    public Work perform(ThrowingRunnable r) {
        internalExecute();
        return new WorkImpl(r, closeableStack);
    }
    
    @Override
    public <R,E> Return<R,Return<R,E>> perform(ThrowingSupplier<R> s) {
        internalExecute();
        return new ReturnImpl<>(s, closeableStack);
    }

    @Override
    public final void execute() throws RuntimeException {
        try { internalExecute(); }
        finally { closeAll(); }
    }

}

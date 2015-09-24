package net.javapla.chains;

import net.javapla.chains.executors.ExecuteVoid;
import net.javapla.chains.function.ThrowingConsumer;

public final class ChainsResourceConsumer<A extends AutoCloseable> extends AbstractChains<ThrowingConsumer<A>, ChainsResourceConsumer<A>> implements ExecuteVoid {

    
    private final A au;
    ChainsResourceConsumer(ThrowingConsumer<A> r, A au) {
        super(r);
        this.au = au;
    }

    @Override
    public void execute() throws RuntimeException {
        try {
            runner.accept(au);
        } catch (Throwable e) {
            handleException(e);
        } finally {
            try {
                au.close();
            } catch (Exception e) {
                handleException(e); 
            }
        }
    }

}

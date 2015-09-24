package net.javapla.chains;

import net.javapla.chains.executors.ExecuteVoid;
import net.javapla.chains.function.ThrowingRunnable;

public class ChainsRunnable extends AbstractChains<ThrowingRunnable, ChainsRunnable> implements ExecuteVoid {

    /**
     * Only used by subclasses
     */
    ChainsRunnable(ThrowingRunnable k) {
        super(k);
    }
    
    
    @Override
    public void execute() throws RuntimeException {
        try {
            runner.run();
        } catch (Throwable e) {
            handleException(e);
        }
    }
}


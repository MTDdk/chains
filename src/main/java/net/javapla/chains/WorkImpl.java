package net.javapla.chains;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import net.javapla.chains.function.ThrowingRunnable;

final class WorkImpl extends AbstractVoid {

    protected final ThrowingRunnable runner;
    WorkImpl(final ThrowingRunnable r, final Queue<AutoCloseable> closeableStack) {
        super(closeableStack);
        runner = r;
    }
    WorkImpl(final ThrowingRunnable r) {
        this(r, new LinkedList<>());
    }

    @Override
    protected Optional<Void> internalExecute() throws RuntimeException {
        try {
            runner.run();
        } catch (Throwable e) {
            handleException(e);
        }
        return Optional.empty();
    }

}

package net.javapla.chains;

import java.util.Optional;

import net.javapla.chains.executors.ExecuteReturn;
import net.javapla.chains.function.ThrowingFunction;

//R = return value
//A = autocloseable
public class ChainsResourceFunction<R, A extends AutoCloseable> extends AbstractChains<ThrowingFunction<A, R>, ChainsResourceFunction<R, A>> implements ExecuteReturn<R> {

    protected final A au;
    protected ChainsResourceFunction(ThrowingFunction<A, R> r, A au) {
        super(r);
        this.au = au;
    }

    @Override
    public Optional<R> execute() throws RuntimeException {
        try {
            return Optional.ofNullable(runner.apply(au));
        } catch (Throwable e) {
            handleException(e); // throws, so no actual need for the last line
        } finally {
            try {
                au.close();
            } catch (Exception e) {
                handleException(e); 
            }
        }
        return Optional.empty();
    }
}

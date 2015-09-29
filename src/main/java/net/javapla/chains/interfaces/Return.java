package net.javapla.chains.interfaces;

import net.javapla.chains.executors.ExecuteReturn;
import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;

public interface Return<R> extends ExecuteReturn<R>, Exceptional<Return<R>> {

    Work perform(ThrowingConsumer<R> c);
    <T> Return<T> perform(ThrowingFunction<R, T> f);
    
}

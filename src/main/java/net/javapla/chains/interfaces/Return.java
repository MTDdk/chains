package net.javapla.chains.interfaces;

import net.javapla.chains.executors.ExecuteReturn;
import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;

public interface Return<R, E> extends ExecuteReturn<R>, Exceptional<E> {

    Work perform(final ThrowingConsumer<R> c);
    <T> Return<T,Return<T,E>> perform(final ThrowingFunction<R, T> f);
}

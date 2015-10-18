package net.javapla.chains.newinterfaces;

import net.javapla.chains.executors.ExecuteReturn;
import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;

public interface Return<R, E> extends ExecuteReturn<R>, Exceptional<E> {

    Work perform(ThrowingConsumer<R> c);
    <T> Return<T,NormalReturn<T>> perform(ThrowingFunction<R, T> f);
}

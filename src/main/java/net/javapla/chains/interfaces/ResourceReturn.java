package net.javapla.chains.interfaces;

import net.javapla.chains.executors.ExecuteReturn;
import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;

public interface ResourceReturn<R extends AutoCloseable> extends Return<R>/*ExecuteReturn<R>, Exceptional<ResourceReturn<R>>*/ {

//    Work perform(ThrowingConsumer<R> c);
//    <T> Return<T> perform(ThrowingFunction<R, T> f);
    <T extends AutoCloseable> ResourceReturn<T> with(ThrowingFunction<R, T> f);

}

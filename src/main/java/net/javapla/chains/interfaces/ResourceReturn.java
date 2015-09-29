package net.javapla.chains.interfaces;

import net.javapla.chains.function.ThrowingFunction;

public interface ResourceReturn<R extends AutoCloseable> extends Return<R> {

    <T extends AutoCloseable> ResourceReturn<T> with(ThrowingFunction<R, T> f);

}

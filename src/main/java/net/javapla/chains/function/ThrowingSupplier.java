package net.javapla.chains.function;


public interface ThrowingSupplier<R> {
    R get() throws Throwable;
}

package net.javapla.chains.function;

//P = parameter
public interface ThrowingConsumer<P> {

    void accept(final P p) throws Throwable;
}

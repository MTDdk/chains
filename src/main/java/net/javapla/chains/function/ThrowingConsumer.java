package net.javapla.chains.function;

//P = parameter
public interface ThrowingConsumer<P> {

    void accept(P p) throws Throwable;
}

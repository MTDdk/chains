package net.javapla.chains.function;


//P = paramater
//R = return value
public interface ThrowingFunction<P, R> {

    R apply(P p) throws Throwable;
}

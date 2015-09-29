package net.javapla.chains.interfaces;

import java.util.function.Consumer;

//handling exceptions
public interface Exceptional<R> {
    <T extends Throwable> R exception(Consumer<? super Throwable> c, @SuppressWarnings("unchecked") Class<T> ... t);
    <T extends Throwable> R exception(Consumer<? super Throwable> c, Class<T> t);
    <T extends Throwable> R exception(Consumer<? super Throwable> c);
    
    //protected static void handleException(Throwable e) throws RuntimeException;
}
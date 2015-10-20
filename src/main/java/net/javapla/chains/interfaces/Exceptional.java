package net.javapla.chains.interfaces;

import java.util.function.Consumer;

//handling exceptions
public interface Exceptional<R> {
    
    /**
     * 
     * @param c
     * @param t
     * @return
     */
    <T extends Throwable> R exception(final Consumer<? super Throwable> c, @SuppressWarnings("unchecked") final Class<T> ... t);
    
    /**
     * Single parameter to reduce overhead
     * @param c
     * @param t
     * @return
     */
    <T extends Throwable> R exception(final Consumer<? super Throwable> c, final Class<T> t);
    
    /**
     * Catch-all
     * @param c
     * @return
     */
    R exception(final Consumer<? super Throwable> c);
    
    //R catchAll(Consumer<? super Throwable> c);
    
    //protected static void handleException(Throwable e) throws RuntimeException;
}
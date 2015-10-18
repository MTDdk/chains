package net.javapla.chains.newinterfaces;

import java.util.function.Consumer;

//handling exceptions
public interface Exceptional<R> {
    
    /**
     * 
     * @param c
     * @param t
     * @return
     */
    <T extends Throwable> R exception(Consumer<? super Throwable> c, @SuppressWarnings("unchecked") Class<T> ... t);
    
    /**
     * Single parameter to reduce overhead
     * @param c
     * @param t
     * @return
     */
    <T extends Throwable> R exception(Consumer<? super Throwable> c, Class<T> t);
    
    /**
     * Catch-all
     * @param c
     * @return
     */
    R exception(Consumer<? super Throwable> c);
    
    //R catchAll(Consumer<? super Throwable> c);
    
    //protected static void handleException(Throwable e) throws RuntimeException;
}
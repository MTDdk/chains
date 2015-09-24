package net.javapla.chains;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.Consumer;


public abstract class AbstractChains<S, R> {
    
    protected final S runner;
    protected AbstractChains(S r) {
        runner = r;
    }
 
    protected final HashMap<Class<? extends Throwable>, Consumer<? super Throwable>> throwables = new HashMap<>();
    /**
     * 
     * @param c
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Throwable> R exception(Consumer<? super Throwable> c, Class<T> ... t) {
        for (Class<T> clazz : t) {
            throwables.put(clazz, c);
        }
        
        return (R) this;
    }
    
    /**
     * Single parameter to reduce overhead
     * @param c
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Throwable> R exception(Consumer<? super Throwable> c, Class<T> t) {
        throwables.put(t, c);
        return (R) this;
    }
    
    /**
     * Catch-all
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Throwable> R exception(Consumer<? super Throwable> c) {
        throwables.put(null, c);
        return (R) this;
    }
    
    protected final void handleException(Throwable e) throws RuntimeException {
        try {
            final Class<? extends Throwable> exceptionClass = e.getClass();
            if (throwables.containsKey(exceptionClass)) {
                throwables.get(exceptionClass).accept(e); //might throw
            } else if (throwables.containsKey(null)) {
                // Fallback if catch-all is specified
                throwables.get(null).accept(e); //might throw
            } else {
                // The exception is nowhere to be found
                // so we try one last thing
                for (Entry<Class<? extends Throwable>, Consumer<? super Throwable>> entry : throwables.entrySet()) {
                    //if (exceptionClass.isAssignableFrom(entry.getKey())) {
                    if (entry.getKey().isInstance(e)) {
                        entry.getValue().accept(e); //might throw
                        return;
                    }
                }
                
                // Still nothing... rethrow
                throw e;
            }
        } catch(Throwable t) {
            throw new RuntimeException(e);
        }
    }
}

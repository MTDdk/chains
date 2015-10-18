package net.javapla.chains;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;

import net.javapla.chains.interfaces.Exceptional;

abstract class AbstractChains<T, R> implements Exceptional<T> {
    
    protected final Queue<AutoCloseable> closeableStack;
    protected AbstractChains(Queue<AutoCloseable> closeableStack) {
        this.closeableStack = closeableStack;
    }
    
    protected void addToStack(AutoCloseable a) {
        closeableStack.add(a);
    }
    protected void closeAll() {
        closeableStack.forEach((auto) -> {
            try {
                auto.close();
            } catch (Exception e) {
                handleException(e);
            }
        });
    }
    
    protected abstract Optional<R> internalExecute();

    protected final HashMap<Class<? extends Throwable>, Consumer<? super Throwable>> throwables = new HashMap<>();
    
    @Override
    @SuppressWarnings("unchecked")
    public <S extends Throwable> T exception(Consumer<? super Throwable> c, Class<S> ... t) {
        for (Class<S> clazz : t) {
            throwables.put(clazz, c);
        }
        return (T) this;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <S extends Throwable> T exception(Consumer<? super Throwable> c, Class<S> t) {
        throwables.put(t, c);
        System.out.println(this.getClass());
        return (T) this;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T exception(Consumer<? super Throwable> c) {
        throwables.put(null, c);
        return (T) this;
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

package net.javapla.chains;

import java.util.Queue;

import net.javapla.chains.function.ThrowingFunction;

//TODO this might not actually be useful
class ResourceInputNormalReturnImpl<P extends AutoCloseable, R, E> extends InputReturnImpl<P, R, E> {
    
    
    ResourceInputNormalReturnImpl(final ThrowingFunction<P, R> r, final P resource, final Queue<AutoCloseable> closeableStack) {
        super(r, resource, closeableStack);
        addToStack(resource);
    }
    
    
}

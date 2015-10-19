package net.javapla.chains;

import java.util.Queue;

import net.javapla.chains.function.ThrowingFunction;

//TODO this might not actually be useful
class ResourceInputNormalReturnImpl<P extends AutoCloseable, R, E> extends InputReturnImpl<P, R, E> {
    
    
    ResourceInputNormalReturnImpl(ThrowingFunction<P, R> r, P resource, Queue<AutoCloseable> closeableStack) {
        super(r, resource, closeableStack);
        addToStack(resource);
    }
    
    
}

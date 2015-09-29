package net.javapla.chains;

import java.util.Queue;

import net.javapla.chains.function.ThrowingFunction;

class ResourceInputNormalReturnImpl<P extends AutoCloseable, R> extends InputReturnImpl<P, R> {
    
    
    ResourceInputNormalReturnImpl(ThrowingFunction<P, R> r, P resource, Queue<AutoCloseable> closeableStack) {
        super(r, resource, closeableStack);
        addToStack(resource);
    }
    
    
}

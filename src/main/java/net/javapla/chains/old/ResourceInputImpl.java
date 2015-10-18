package net.javapla.chains.old;

import java.util.Queue;

import net.javapla.chains.function.ThrowingConsumer;

class ResourceInputImpl<A extends AutoCloseable> extends InputImpl<A> {
    
    
    ResourceInputImpl(ThrowingConsumer<A> r, A resource, Queue<AutoCloseable> closeableStack) {
        super(r, resource, closeableStack);
        addToStack(resource);
    }
    

}

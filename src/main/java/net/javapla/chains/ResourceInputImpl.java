package net.javapla.chains;

import java.util.Queue;

import net.javapla.chains.function.ThrowingConsumer;

final class ResourceInputImpl<A extends AutoCloseable> extends InputImpl<A> {
    
    
    ResourceInputImpl(final ThrowingConsumer<A> r, final A resource, final Queue<AutoCloseable> closeableStack) {
        super(r, resource, closeableStack);
        addToStack(resource);
    }
    

}

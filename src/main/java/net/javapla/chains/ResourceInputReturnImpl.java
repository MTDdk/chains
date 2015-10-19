package net.javapla.chains;

import java.util.Queue;

import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;
import net.javapla.chains.interfaces.ResourceReturn;
import net.javapla.chains.interfaces.Return;
import net.javapla.chains.interfaces.Work;

final class ResourceInputReturnImpl<P extends AutoCloseable, R extends AutoCloseable> extends ResourceInputNormalReturnImpl<P, R, ResourceReturn<R>> implements ResourceReturn<R> {
    
    
    ResourceInputReturnImpl(ThrowingFunction<P, R> r, P resource, Queue<AutoCloseable> closeableStack) {
        super(r, resource, closeableStack);
    }
    
    @Override
    public <T extends AutoCloseable> ResourceReturn<T> with(ThrowingFunction<R, T> f) {
        R r = internalExecute().get();
        return new ResourceInputReturnImpl<>(f, r, closeableStack);
    }
    
    @Override
    public Work perform(ThrowingConsumer<R> c) {
        R r = internalExecute().get();
        return new ResourceInputImpl<>(c, r, closeableStack);
    }
    
    @Override
    public <T> Return<T,Return<T,ResourceReturn<R>>> perform(ThrowingFunction<R, T> f) {
        R r = internalExecute().get();
        return new ResourceInputNormalReturnImpl<>(f, r, closeableStack);
    }

}

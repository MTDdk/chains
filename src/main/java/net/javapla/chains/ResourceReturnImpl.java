package net.javapla.chains;

import java.util.LinkedList;

import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;
import net.javapla.chains.function.ThrowingSupplier;
import net.javapla.chains.interfaces.ResourceReturn;
import net.javapla.chains.interfaces.Return;
import net.javapla.chains.interfaces.Work;

final class ResourceReturnImpl<R extends AutoCloseable> extends ReturnImpl<R, ResourceReturn<R>> implements ResourceReturn<R> {

    
    ResourceReturnImpl(ThrowingSupplier<R> r) {
        super(r, new LinkedList<>());
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

    @Override
    public <T extends AutoCloseable> ResourceReturn<T> with(ThrowingFunction<R,T> f) {
        R r = internalExecute().get();
        return new ResourceInputReturnImpl<>(f, r, closeableStack);
    }
}

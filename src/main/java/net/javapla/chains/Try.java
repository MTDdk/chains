package net.javapla.chains;

import java.util.LinkedList;

import net.javapla.chains.function.ThrowingRunnable;
import net.javapla.chains.function.ThrowingSupplier;
import net.javapla.chains.interfaces.ResourceReturn;
import net.javapla.chains.interfaces.Return;
import net.javapla.chains.interfaces.Work;

public interface Try {

    //Try-with-resources
    static <A extends AutoCloseable> ResourceReturn<A> with(final ThrowingSupplier<A> s) {
        return new ResourceReturnImpl<>(s);
    }
    
    
    //Try
    static Work perform(ThrowingRunnable r) {
        return new WorkImpl(r);
    }
    static <R,E> Return<R, Return<R,E>> perform(final ThrowingSupplier<R> s) {
        return new ReturnImpl<>(s, new LinkedList<>());
    }
    
}

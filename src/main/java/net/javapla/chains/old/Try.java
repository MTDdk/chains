package net.javapla.chains.old;

import java.util.LinkedList;

import net.javapla.chains.function.ThrowingRunnable;
import net.javapla.chains.function.ThrowingSupplier;
import net.javapla.chains.interfaces.ResourceReturn;
import net.javapla.chains.interfaces.Return;
import net.javapla.chains.interfaces.Work;

public interface Try {

    //Try-with-resources
    static <A extends AutoCloseable> ResourceReturn<A> with(ThrowingSupplier<A> s) {
        return new ResourceReturnImpl<>(s);
    }
    
    
    //Try
    static Work perform(ThrowingRunnable r) {
        return new WorkImpl(r);
    }
    static <R> Return<R> perform(ThrowingSupplier<R> s) {
        return new ReturnImpl<>(s, new LinkedList<>());
    }
    
}

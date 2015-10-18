package net.javapla.chains.newinterfaces;

import net.javapla.chains.executors.ExecuteVoid;
import net.javapla.chains.function.ThrowingRunnable;
import net.javapla.chains.function.ThrowingSupplier;

public interface Work extends ExecuteVoid, Exceptional<Work> {

    Work perform(ThrowingRunnable r);
    <R> Return<R,NormalReturn<R>> perform(ThrowingSupplier<R> s);
    
}

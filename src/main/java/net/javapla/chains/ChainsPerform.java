package net.javapla.chains;

import net.javapla.chains.function.ThrowingRunnable;
import net.javapla.chains.function.ThrowingSupplier;

public interface ChainsPerform {

    default ChainsRunnable perform(ThrowingRunnable r) {
        return new ChainsRunnable(r);
    }
    
    default <T> ChainsSupplier<T> perform(ThrowingSupplier<T> s) {
        return new ChainsSupplier<T>(s);
    }
}

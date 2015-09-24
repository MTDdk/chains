package net.javapla.chains;

import net.javapla.chains.function.ThrowingConsumer;
import net.javapla.chains.function.ThrowingFunction;
import net.javapla.chains.function.ThrowingRunnable;
import net.javapla.chains.function.ThrowingSupplier;

public class Chains {
    
    public static final ChainsRunnable perform(ThrowingRunnable r) {
        return new ChainsRunnable(r);
    }
    
    public static final <R> ChainsSupplier<R> perform(ThrowingSupplier<R> s) {
        return new ChainsSupplier<>(s);
    }
    
    public static final <A extends AutoCloseable> ConsumerResource<A> tryWith(A c) {
        return new ConsumerResource<>(c);
    }

    
    public static final class ConsumerResource<A extends AutoCloseable> {
        private final A ac;
        public ConsumerResource(A a) {
            this.ac = a;
        }
        
        public final ChainsResourceConsumer<A> perform(ThrowingConsumer<A> c) {
            return new ChainsResourceConsumer<>(c, ac);
        }
        
        public final <R> ChainsResourceFunction<R,A> perform(ThrowingFunction<A, R> f) {
            return new ChainsResourceFunction<>(f, ac);
        }
    }
}

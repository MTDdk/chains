package net.javapla.chains.function;

public interface ExceptionFunction <T extends Throwable> {

    void accept(T t);
}

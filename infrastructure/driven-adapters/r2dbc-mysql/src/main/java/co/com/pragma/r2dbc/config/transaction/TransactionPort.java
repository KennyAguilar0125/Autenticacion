package co.com.pragma.r2dbc.config.transaction;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

public interface TransactionPort {
    <T> Mono<T> write(Supplier<Mono<T>> action);
    <T> Flux<T> writeMany(Supplier<Publisher<T>> work);
}

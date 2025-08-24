package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserUseCase {

    Mono<User> findById(String idUser);

    Flux<User> findAll();

    Mono<User> save(User user);

    Mono<Void> deleteById(String idUser);

    Mono<User> findByEmail(String email);

    Mono<User> findByDocumentNumber(String documentNumber);
}

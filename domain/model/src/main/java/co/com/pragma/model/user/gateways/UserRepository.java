package co.com.pragma.model.user.gateways;

import co.com.pragma.model.user.security.LogIn;
import co.com.pragma.model.user.security.ResponseToken;
import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findById(String idUser);

    Flux<User> findAll();

    Mono<User> save(User user);

    Mono<User> findByEmail(String email);

    Mono<User> findByDocumentNumber(String documentNumber);

    Mono<ResponseToken> login(LogIn logIn);
}

package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.security.LogIn;
import co.com.pragma.model.security.ResponseToken;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserUseCase {

    Mono<User> findById(String idUser);

    Flux<User> findAll();

    Mono<User> save(User user);

    Mono<User> findByEmail(String email);

    Mono<User> findByDocumentNumber(String documentNumber);

    Flux<User> saveAll(List<User> users);

    Mono<ResponseToken> login(LogIn dto);
}

package co.com.pragma.usecase.user;

import co.com.pragma.model.exception.FieldAlreadyRegisteredException;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.security.LogIn;
import co.com.pragma.model.security.ResponseToken;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> findById(String idUser) {
        return userRepository.findById(idUser);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    public Mono<User> save(User user) {
        return findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new FieldAlreadyRegisteredException("email")))
                .switchIfEmpty(
                        findByDocumentNumber(user.getDocumentNumber())
                                .flatMap(existing -> Mono.<User>error(new FieldAlreadyRegisteredException("documentNumber")))
                                .switchIfEmpty(
                                        // Guardamos el usuario dentro de la transacción
                                        saveUser(user)
                                )
                );
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> findByDocumentNumber(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber);
    }

    public Flux<User> saveAll(List<User> users) {
        return Flux.fromIterable(users)
                .flatMap(this::save);

    }

    @Override
    public Mono<ResponseToken> login(LogIn dto) {
        return userRepository.login(dto);
    }


}

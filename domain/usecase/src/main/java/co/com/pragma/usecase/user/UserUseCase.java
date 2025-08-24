package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository userRepository;

    public Mono<User> findById(String idUser) {
        return userRepository.findById(idUser);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> save(User user) {
        return findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new IllegalArgumentException("El email ya está registrado")))
                .switchIfEmpty(
                        findByDocumentNumber(user.getDocumentNumber())
                                .flatMap(existing -> Mono.<User>error(new IllegalArgumentException("El documentNumber ya está registrado")))
                                .switchIfEmpty(
                                        userRepository.save(user)
                                )
                );
    }

    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> findByDocumentNumber(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber);
    }
}

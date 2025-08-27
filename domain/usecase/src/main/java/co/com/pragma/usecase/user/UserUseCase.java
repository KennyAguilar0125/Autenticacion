package co.com.pragma.usecase.user;

import co.com.pragma.model.common.TransactionPort;
import co.com.pragma.model.exeptions.FieldAlreadyRegisteredException;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCase implements IUserUseCase {

    private final UserRepository userRepository;
    private final TransactionPort transactionPort;

    public Mono<User> findById(String idUser) {
        return userRepository.findById(idUser);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> save(User user) {
        return transactionPort.write(() ->
                findByEmail(user.getEmail())
                        .flatMap(existing -> Mono.<User>error(new FieldAlreadyRegisteredException("email")))
                        .switchIfEmpty(
                                findByDocumentNumber(user.getDocumentNumber())
                                        .flatMap(existing -> Mono.<User>error(new FieldAlreadyRegisteredException("documentNumber")))
                                        .switchIfEmpty(
                                                // Guardamos el usuario dentro de la transacción
                                                userRepository.save(user)
                                        )
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
        return transactionPort.writeMany(() ->
                Flux.fromIterable(users)
                        .flatMap(this::save)
        );
    }
}

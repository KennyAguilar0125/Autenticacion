package co.com.pragma.usecase.user;

import co.com.pragma.model.common.TransactionPort;
import co.com.pragma.model.exception.FieldAlreadyRegisteredException;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Supplier;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionPort transactionPort;

    @InjectMocks
    private UserUseCase userUseCase;

    UUID uuid = UUID.randomUUID();
    LocalDate date = LocalDate.of(1999, 1, 25);
    BigDecimal salary = BigDecimal.TEN;
    private final User sampleUser = User.builder()
            .idUser(uuid.toString())
            .documentNumber("1234")
            .names("Kenny Alejandro")
            .lastNames("Carreño Aguilar")
            .birthDate(date)
            .address("calle con carrera")
            .phoneNumber("12345")
            .email("correo@gmail.com")
            .baseSalary(salary)
            .build();


    @Test
    void shouldThrowError_WhenEmailAlreadyExists() {
        // Arrange
        Mockito.when(userRepository.findByEmail(sampleUser.getEmail()))
                .thenReturn(Mono.just(sampleUser));
        Mockito.when(transactionPort.write(Mockito.any())).thenAnswer(invocation -> {
            Supplier<Mono<User>> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        // Act
        Mono<User> result = userUseCase.save(sampleUser);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof FieldAlreadyRegisteredException &&
                        throwable.getMessage().contains("email"))
                .verify();
    }

    @Test
    void shouldThrowError_WhenDocumentNumberAlreadyExists() {
        // Arrange
        Mockito.when(userRepository.findByEmail(sampleUser.getEmail()))
                .thenReturn(Mono.empty()); // no hay conflicto por email

        Mockito.when(userRepository.findByDocumentNumber(sampleUser.getDocumentNumber()))
                .thenReturn(Mono.just(sampleUser)); // conflicto por documento -> debe lanzar error

        Mockito.when(transactionPort.write(Mockito.any()))
                .thenAnswer(invocation -> {
                    Supplier<Mono<User>> supplier = invocation.getArgument(0);
                    return supplier.get();
                });

        // Act
        Mono<User> result = userUseCase.save(sampleUser);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof FieldAlreadyRegisteredException &&
                        throwable.getMessage().contains("documentNumber"))
                .verify();
    }
}

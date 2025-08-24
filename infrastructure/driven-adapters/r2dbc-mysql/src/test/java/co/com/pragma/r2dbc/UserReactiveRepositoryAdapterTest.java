package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    UserReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    UUID uuid = UUID.randomUUID();
    LocalDate date = LocalDate.of(1999, 1, 25);
    BigDecimal salary = BigDecimal.TEN;

    private final UserEntity userEntity = UserEntity.builder()
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

    private final User user = User.builder()
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
    void shouldFindById() {

        when(mapper.map(userEntity, User.class)).thenReturn(user);

        when(repository.findById(uuid.toString())).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findById(uuid.toString());

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getIdUser().equals(uuid.toString())
                        && t.getDocumentNumber().equals("1234"))
                .verifyComplete();
    }

    @Test
    void shouldFindAll() {
        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(repository.findAll()).thenReturn(Flux.just(userEntity));

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldSave() {
        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(repository.save(userEntity)).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFindByEmail() {

        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(repository.findByEmail("correo@gmail.com")).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findByEmail("correo@gmail.com");

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getIdUser().equals(uuid.toString())
                        && t.getEmail().equals("correo@gmail.com"))
                .verifyComplete();
    }

    @Test
    void shouldFindByDocumentNumber() {

        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(repository.findByDocumentNumber("1234")).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findByDocumentNumber("1234");

        StepVerifier.create(result)
                .expectNextMatches(t -> t.getIdUser().equals(uuid.toString())
                        && t.getDocumentNumber().equals("1234"))
                .verifyComplete();
    }
}

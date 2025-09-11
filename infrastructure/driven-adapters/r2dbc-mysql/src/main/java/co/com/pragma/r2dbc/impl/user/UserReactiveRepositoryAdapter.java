package co.com.pragma.r2dbc.impl.user;

import co.com.pragma.model.rol.gateways.RolRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.security.LogIn;
import co.com.pragma.model.security.ResponseToken;
import co.com.pragma.r2dbc.config.transaction.TransactionPort;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.securityjwt.jwt.JwtProvider;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        UserReactiveRepository
        > implements UserRepository {
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private RolRepository rolRepository;

    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionPort transactionPort) {
        super(repository, mapper, d -> mapper.map(d, User.class), transactionPort);
    }

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setPasswordEncoder(org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setRolRepository(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toEntity);
    }

    public Mono<User> findByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber).map(this::toEntity);
    }

    public Mono<User> save(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return saveData(toData(entity))
                .map(this::toEntity);
    }

    @Override
    public Mono<ResponseToken> login(LogIn logIn) {
        return findByEmail(logIn.getEmail())
                .filter(user -> passwordEncoder.matches(logIn.getPassword(), user.getPassword()))
                .flatMap(user -> rolRepository.findById(user.getIdRol())
                        .map(role -> new ResponseToken(jwtProvider.generateToken(user, role.getCode()))))
                .switchIfEmpty(Mono.error(new Throwable("bad credentials")));
    }
}

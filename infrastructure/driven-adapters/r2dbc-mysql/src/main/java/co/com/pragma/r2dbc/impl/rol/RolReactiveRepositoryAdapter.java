package co.com.pragma.r2dbc.impl.rol;

import co.com.pragma.model.rol.Rol;
import co.com.pragma.model.rol.gateways.RolRepository;
import co.com.pragma.r2dbc.config.transaction.TransactionPort;
import co.com.pragma.r2dbc.entity.RolEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class RolReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        Rol,
        RolEntity,
        String,
        RolReactiveRepository
        > implements RolRepository {
    public RolReactiveRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper, TransactionPort transactionPort) {
        super(repository, mapper, d -> mapper.map(d, Rol.class), transactionPort);
    }
}

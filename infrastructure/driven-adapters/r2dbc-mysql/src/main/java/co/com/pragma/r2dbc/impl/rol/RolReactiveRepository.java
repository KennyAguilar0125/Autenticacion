package co.com.pragma.r2dbc.impl.rol;

import co.com.pragma.r2dbc.entity.RolEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RolReactiveRepository extends ReactiveCrudRepository<RolEntity, String>, ReactiveQueryByExampleExecutor<RolEntity> {
}

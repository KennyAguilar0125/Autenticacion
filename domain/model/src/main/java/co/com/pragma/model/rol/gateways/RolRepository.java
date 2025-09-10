package co.com.pragma.model.rol.gateways;

import co.com.pragma.model.rol.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RolRepository {
    Flux<Rol> findAll();

    Mono<Rol> findById(String idRol);

    Mono<Rol> save(Rol user);
}

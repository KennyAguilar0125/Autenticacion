package co.com.pragma.usecase.rol;

import co.com.pragma.model.rol.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRolUseCase {

    Mono<Rol> findById(String idRol);

    Flux<Rol> findAll();

    Mono<Rol> save(Rol rol);
}

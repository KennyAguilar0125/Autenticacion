package co.com.pragma.usecase.rol;

import co.com.pragma.model.rol.Rol;
import co.com.pragma.model.rol.gateways.RolRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RolUseCase implements IRolUseCase {

    private final RolRepository rolRepository;

    public Mono<Rol> findById(String idRol) {
        return rolRepository.findById(idRol);
    }

    public Flux<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Mono<Rol> save(Rol rol) {
        return rolRepository.save(rol);
    }
}

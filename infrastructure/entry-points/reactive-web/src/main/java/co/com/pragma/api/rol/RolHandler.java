package co.com.pragma.api.rol;

import co.com.pragma.api.rol.dto.RolDTO;
import co.com.pragma.api.rol.mapper.RolDTOMapper;
import co.com.pragma.api.validation.ValidationHandler;
import co.com.pragma.usecase.rol.IRolUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class RolHandler {
    private final IRolUseCase rolUseCase;
    private final RolDTOMapper rolDTOMapper;
    private final ValidationHandler validationHandler;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        log.debug("Entra servicio findAll en Roles");
        return rolUseCase.findAll()
                .collectList()
                .flatMap(listRol -> ServerResponse.ok().bodyValue(rolDTOMapper.toResponseList(listRol)));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        log.debug("Entra servicio findById en Roles");

        final String id = request.pathVariable("idRol");

        return rolUseCase.findById(id)
                .flatMap(rol -> ServerResponse.ok().bodyValue(rolDTOMapper.toResponse(rol)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Rol {} no encontrado", id);
                    return ServerResponse.notFound().build();
                }));
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.debug("Entra servicio save en Roles");
        return serverRequest.bodyToMono(RolDTO.class)
                .flatMap(validationHandler::validate)
                .map(rolDTOMapper::toModel)
                .flatMap(rolUseCase::save)
                .map(rolDTOMapper::toResponse)
                .flatMap(rolCreate -> {
                    log.info("Rol Creado: {}", rolCreate.getIdRol());
                    return ServerResponse.status(HttpStatus.CREATED).bodyValue(rolCreate);
                });
    }
}

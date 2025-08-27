package co.com.pragma.api;

import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.api.mapper.UserDTOMapper;
import co.com.pragma.r2dbc.helper.utilities.ValidationHandler;
import co.com.pragma.usecase.user.IUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final IUserUseCase userUseCase;
    private final UserDTOMapper userDTOMapper;
    private final ValidationHandler validationHandler;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        log.debug("Entra servicio findAll en Usuarios");
        return userUseCase.findAll()
                .collectList()
                .flatMap(listUser -> ServerResponse.ok().bodyValue(userDTOMapper.toResponseList(listUser)));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        log.debug("Entra servicio findById en Usuarios");

        final String id = request.pathVariable("idUsuario");

        return userUseCase.findById(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(userDTOMapper.toResponse(user)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Usuario {} no encontrado", id);
                    return ServerResponse.notFound().build();
                }));
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.debug("Entra servicio save en Usuarios");
        return serverRequest.bodyToMono(UserDTO.class)
                .flatMap(validationHandler::validate)
                .map(userDTOMapper::toModel)
                .flatMap(userUseCase::save)
                .map(userDTOMapper::toResponse)
                .flatMap(userCreate -> {
                    log.info("Usuario Creado: {}", userCreate.getIdUser());
                    return ServerResponse.status(HttpStatus.CREATED).bodyValue(userCreate);
                });
    }

    public Mono<ServerResponse> saveAll(ServerRequest serverRequest) {
        log.debug("Entra servicio save en Usuarios");

        // Obtener el cuerpo de la solicitud como una lista de usuarios (UserDTO)
        return serverRequest.bodyToMono(new ParameterizedTypeReference<List<UserDTO>>() {})
                .flatMap(validationHandler::validateList)  // Validar la lista de usuarios
                .map(userDTOMapper::toModelList)  // Mapear la lista de UserDTO a modelos (User)
                .flatMapMany(userUseCase::saveAll)  // Guardar todos los usuarios dentro de la transacción y obtener un Flux<User>
                .map(userDTOMapper::toResponse)  // Mapear cada usuario guardado a UserDTO
                .collectList()  // Convertir el Flux<User> en Mono<List<UserDTO>> para devolver la respuesta
                .flatMap(savedUsers -> {
                    log.info("Usuarios creados: {}", savedUsers.stream()
                            .map(UserDTO::getIdUser)
                            .collect(Collectors.toList()));
                    return ServerResponse.status(HttpStatus.CREATED).bodyValue(savedUsers);
                });
    }

    public Mono<ServerResponse> findByDocumentNumber(ServerRequest request) {
        log.debug("Entra servicio findByDocumentNumber en Usuarios");

        final String documentNumber = request.pathVariable("documentNumber");

        return userUseCase.findByDocumentNumber(documentNumber)
                .flatMap(user -> ServerResponse.ok().bodyValue(userDTOMapper.toResponse(user)))
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Usuario {} no encontrado", documentNumber);
                    return ServerResponse.noContent().build();
                }));
    }
}

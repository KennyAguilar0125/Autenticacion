package co.com.pragma.api;

import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.api.mapper.UserDTOMapper;
import co.com.pragma.r2dbc.helper.utilities.ValidationHandler;
import co.com.pragma.usecase.user.IUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final IUserUseCase userUseCase;
    private final UserDTOMapper userDTOMapper;
    private final ValidationHandler validationHandler;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return userUseCase.findAll()
                .collectList()
                .flatMap(listUser -> ServerResponse.ok().bodyValue(userDTOMapper.toResponseList(listUser)))
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("idUsuario");

        return userUseCase.findById(id)
                .flatMap(user -> ServerResponse.ok().bodyValue(userDTOMapper.toResponse(user)))
                .onErrorResume(ex -> ServerResponse.notFound().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDTO.class)
                .flatMap(validationHandler::validate)
                .map(userDTOMapper::toModel)
                .flatMap(userUseCase::save)
                .map(userDTOMapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("idUsuario");

        return userUseCase.deleteById(id)
                .then(ServerResponse.noContent().build());
    }
}

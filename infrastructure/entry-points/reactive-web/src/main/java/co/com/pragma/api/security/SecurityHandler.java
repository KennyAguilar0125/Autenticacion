package co.com.pragma.api.security;

import co.com.pragma.api.security.dto.LogInDTO;
import co.com.pragma.api.security.mapper.LoginDTOMapper;
import co.com.pragma.api.validation.ValidationHandler;
import co.com.pragma.model.security.ResponseToken;
import co.com.pragma.usecase.user.IUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityHandler {
    private final IUserUseCase userUseCase;
    private final LoginDTOMapper loginDTOMapper;
    private final ValidationHandler validationHandler;

    public Mono<ServerResponse> logIn(ServerRequest request) {

        return request.bodyToMono(LogInDTO.class)
                .flatMap(validationHandler::validate)
                .map(loginDTOMapper::toModel)
                .flatMap(dto -> {
                    log.info("token generado");
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(userUseCase.login(dto), ResponseToken.class);
                });
    }
}

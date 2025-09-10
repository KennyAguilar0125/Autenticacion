package co.com.pragma.api.security;

import co.com.pragma.model.user.security.LogIn;
import co.com.pragma.model.user.security.ResponseToken;
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

    public Mono<ServerResponse> logIn(ServerRequest request) {

        return request.bodyToMono(LogIn.class)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userUseCase.login(dto), ResponseToken.class));
    }
}

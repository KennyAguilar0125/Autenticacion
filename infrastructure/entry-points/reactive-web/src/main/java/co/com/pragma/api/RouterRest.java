package co.com.pragma.api;

import co.com.pragma.api.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    @RouterOperations
            ({
                    @RouterOperation(
                            path = "/api/v1/usuarios",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = Handler.class,
                            beanMethod = "findAll",

                            operation = @Operation(
                                    operationId = "findAll",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "trae todos los usuarios registrados.",
                                                    content = @Content(schema = @Schema(
                                                            implementation = UserDTO.class
                                                    ))
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/api/v1/usuarios/{idUsuario}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = Handler.class,
                            beanMethod = "findById",

                            operation = @Operation(
                                    operationId = "findById",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "trae un usuario por id.",
                                                    content = @Content(schema = @Schema(
                                                            implementation = UserDTO.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "no se encontro usuario por id"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(
                                                    in = ParameterIn.PATH,
                                                    name = "idUsuario"
                                            )
                                    }
                            )
                    ),
                    @RouterOperation(
                            path = "/api/v1/usuarios",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.POST,
                            beanClass = Handler.class,
                            beanMethod = "save",

                            operation = @Operation(
                                    operationId = "save",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Se guardo correctamente",
                                                    content = @Content(schema = @Schema(
                                                            implementation = UserDTO.class
                                                    ))
                                            )
                                    },
                                    requestBody = @RequestBody(
                                            content = @Content(schema = @Schema(
                                                    implementation = UserDTO.class
                                            )
                                            )
                                    )
                            )
                    )
            })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/v1/usuarios"), handler::findAll)
                .andRoute(GET("/api/v1/usuarios/{idUsuario}"), handler::findById)
                .andRoute(POST("/api/v1/usuarios"), handler::save)
                .andRoute(POST("/api/v1/usuarios/all"), handler::saveAll);
    }
}

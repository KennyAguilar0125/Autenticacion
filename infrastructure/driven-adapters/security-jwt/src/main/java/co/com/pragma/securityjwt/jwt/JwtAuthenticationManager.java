package co.com.pragma.securityjwt.jwt;

import co.com.pragma.model.rol.gateways.RolRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtProvider jwtProvider;
    private final RolRepository rolRepository;

    public JwtAuthenticationManager(JwtProvider jwtProvider, RolRepository rolRepository) {
        this.jwtProvider = jwtProvider;
        this.rolRepository = rolRepository;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.getClaims(auth.getCredentials().toString()))
                .log()
                .onErrorResume(e -> Mono.error(new Throwable("bad token")))
                .flatMap(claims -> {
                    String roleId = (String) claims.get("rol");

                    return rolRepository.findById(roleId)  // Suponemos que este método devuelve un Mono<String>
                            .map(roleName -> new UsernamePasswordAuthenticationToken(
                                    claims.getSubject(),  // El 'sub' es el usuario
                                    null,  // No usamos la contraseña en este caso
                                    Stream.of(new SimpleGrantedAuthority("ROLE_" + roleName.getCode()))  // Convertimos el rol en autoridad
                                            .collect(Collectors.toList())  // Lista de autoridades
                            ));

                });
    }
}

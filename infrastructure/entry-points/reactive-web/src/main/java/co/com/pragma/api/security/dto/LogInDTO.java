package co.com.pragma.api.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LogInDTO {
    @NotBlank(message = "no puede estar vacio o nulo")
    @Email(message = "debe tener formato valido")
    String email;
    @NotBlank(message = "no puede estar vacio o nulo")
    String password;
}

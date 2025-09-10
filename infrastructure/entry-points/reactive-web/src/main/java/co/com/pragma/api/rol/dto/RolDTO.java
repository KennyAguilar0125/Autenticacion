package co.com.pragma.api.rol.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RolDTO {

    private String idRol;
    @NotBlank(message = "no puede estar vacio o nulo")
    private String name;
    @NotBlank(message = "no puede estar vacio o nulo")
    private String description;
}

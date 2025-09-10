package co.com.pragma.model.rol;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Rol {
    private String idRol;
    private String code;
    private String name;
    private String description;
}

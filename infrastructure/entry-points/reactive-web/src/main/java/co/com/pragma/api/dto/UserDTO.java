package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {
    private String idUser;
    @NotBlank(message = "no puede estar vacio o nulo")
    private String documentNumber;
    @NotBlank(message = "no puede estar vacio o nulo")
    private String names;
    @NotBlank(message = "no puede estar vacio o nulo")
    private String lastNames;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    @NotBlank(message = "no puede estar vacio o nulo")
    @Email(message = "debe tener formato valido")
    private String email;
    @DecimalMin(value = "0.00", message = "minimo debe ser de 0")
    @DecimalMax(value = "15000000", message = "maximo debe ser 15000000")
    @Digits(integer = 15, fraction = 0, message = "Formato de salario inválido")
    private BigDecimal baseSalary;
}

package co.com.pragma.model.user;

import co.com.pragma.model.rol.Rol;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String idUser;
    private String documentNumber;
    private String names;
    private String lastNames;
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
    private String email;
    private BigDecimal baseSalary;
    private String password;
    private String idRol;

}

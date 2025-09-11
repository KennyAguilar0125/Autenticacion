package co.com.pragma.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column("id_usuario")
    private String idUser;

    @Column("numero_documento")
    private String documentNumber;

    @Column("nombres")
    private String names;

    @Column("apellidos")
    private String lastNames;

    @Column("fecha_nacimiento")
    private LocalDate birthDate;

    @Column("direccion")
    private String address;

    @Column("telefono")
    private String phoneNumber;

    @Column("correo_electronico")
    private String email;

    @Column("salario_base")
    private BigDecimal baseSalary;

    @Column("contrasena")
    private String password;

    @Column("id_rol")
    private String idRol;
}

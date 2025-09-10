package co.com.pragma.r2dbc.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column("id_rol")
    private String idRol;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}

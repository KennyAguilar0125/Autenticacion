package co.com.pragma.api.rol.mapper;

import co.com.pragma.api.rol.dto.RolDTO;
import co.com.pragma.model.rol.Rol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolDTOMapper {
    RolDTO toResponse(Rol rol);

    List<RolDTO> toResponseList(List<Rol> rol);

    Rol toModel(RolDTO RolDTO);

    List<Rol> toModelList(List<RolDTO> userDTO);
}

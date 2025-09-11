package co.com.pragma.api.security.mapper;

import co.com.pragma.api.security.dto.LogInDTO;
import co.com.pragma.model.security.LogIn;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoginDTOMapper {
    LogInDTO toResponse(LogIn rol);

    List<LogInDTO> toResponseList(List<LogIn> rol);

    LogIn toModel(LogInDTO logInDTO);

    List<LogIn> toModelList(List<LogInDTO> logInDTO);
}

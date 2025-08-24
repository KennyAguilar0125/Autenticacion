package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
    UserDTO toResponse(User user);

    List<UserDTO> toResponseList(List<User> user);

    User toModel(UserDTO userDTO);
}

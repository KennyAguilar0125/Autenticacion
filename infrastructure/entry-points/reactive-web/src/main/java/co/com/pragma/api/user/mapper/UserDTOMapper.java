package co.com.pragma.api.user.mapper;

import co.com.pragma.api.user.dto.UserDTO;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
    UserDTO toResponse(User user);

    List<UserDTO> toResponseList(List<User> user);

    User toModel(UserDTO userDTO);

    List<User> toModelList(List<UserDTO> userDTO);
}

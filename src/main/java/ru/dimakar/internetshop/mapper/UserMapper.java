package ru.dimakar.internetshop.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.dimakar.internetshop.dto.SignUpRequest;
import ru.dimakar.internetshop.dto.UserDto;
import ru.dimakar.internetshop.dto.UserRoles;
import ru.dimakar.internetshop.model.UserModel;
import ru.dimakar.internetshop.repository.RoleRepository;

@Component
public class UserMapper {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public UserDto userToDto(UserModel userModel) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userModel,
                        UserDto.class)
                .withRole(UserRoles.valueOf(userModel.getRole().getName()));
    }

    public UserModel signUpRequestToModel(SignUpRequest signUpRequest) {
        ModelMapper modelMapper = new ModelMapper();
        UserModel userModel = modelMapper.map(signUpRequest,
                UserModel.class);
        userModel.setEmail(userModel.getEmail().toLowerCase());
        userModel.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userModel;
    }
}

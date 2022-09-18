package ru.dimakar.internetshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimakar.internetshop.dto.SignUpRequest;
import ru.dimakar.internetshop.dto.UserDto;
import ru.dimakar.internetshop.dto.UserRoles;
import ru.dimakar.internetshop.ex.BadRequestException;
import ru.dimakar.internetshop.mapper.UserMapper;
import ru.dimakar.internetshop.model.RoleModel;
import ru.dimakar.internetshop.model.UserModel;
import ru.dimakar.internetshop.repository.RoleRepository;
import ru.dimakar.internetshop.repository.UserRepository;
import ru.dimakar.internetshop.security.User;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        createRoles();
        createAdmin();
    }

    private void createRoles() {
        if (roleRepository.findRoleModelByName(UserRoles.ROLE_USER.toString()) == null)
            roleRepository.save(RoleModel.builder().name(UserRoles.ROLE_USER.toString()).build());
        if (roleRepository.findRoleModelByName(UserRoles.ROLE_ADMINISTRATOR.toString()) == null)
            roleRepository.save(RoleModel.builder().name(UserRoles.ROLE_ADMINISTRATOR.toString()).build());
    }


    private void createAdmin() {
        RoleModel adminRole = roleRepository
                .findRoleModelByName(UserRoles.ROLE_ADMINISTRATOR.toString());
        if (userRepository.findAllByRole(adminRole).isEmpty()) {
            userRepository.save(UserModel.builder()
                    .role(adminRole)
                    .email("admin")
                    .password(passwordEncoder.encode("admin"))
                    .build());
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findUserModelByEmailIgnoreCase(email.toLowerCase());
        if (userModel == null) {
            throw new UsernameNotFoundException("User is not found: " + email);
        }
        return new User(userModel);
    }

    @Transactional
    public UserDto createUser(SignUpRequest signUpRequest) {
        UserModel userModel = userRepository.findUserModelByEmailIgnoreCase(signUpRequest.getEmail());
        if (userModel != null) {
            throw new BadRequestException("User already exists!");
        }
        userModel = userMapper.signUpRequestToModel(signUpRequest);
        RoleModel roleModelUser = roleRepository.findRoleModelByName(UserRoles.ROLE_USER.toString());
        if (roleModelUser == null)
            throw new RuntimeException("System doesn't have needed role 'User'");
        userModel.setRole(roleModelUser);
        userModel = userRepository.save(userModel);
        return userMapper.userToDto(userModel);
    }
}

package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.UserLoginDTO;
import org.scd.model.dto.UserRegisterDTO;
import org.scd.repository.RoleRepository;
import org.scd.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;


public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) throws BusinessException {

        if (Objects.isNull(userLoginDTO)) {
            throw new BusinessException(401, "Body null !");
        }

        if (Objects.isNull(userLoginDTO.getEmail())) {
            throw new BusinessException(400, "Email cannot be null ! ");
        }

        if (Objects.isNull(userLoginDTO.getPassword())) {
            throw new BusinessException(400, "Password cannot be null !");
        }

        final User user = userRepository.findByEmail(userLoginDTO.getEmail());

        if (Objects.isNull(user)) {
            throw new BusinessException(401, "Bad credentials !");
        }

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "Bad credentials !");
        }

        return user;
    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws BusinessException {

        if (Objects.isNull(userRegisterDTO)) {
            throw new BusinessException(401, "Body null !");
        }

        if (Objects.isNull(userRegisterDTO.getFirstName())) {
            throw new BusinessException(401, "Provide First Name!");
        }

        if (Objects.isNull(userRegisterDTO.getLastName())) {
            throw new BusinessException(401, "Provide Last Name !");
        }

        if (Objects.isNull(userRegisterDTO.getEmail())) {
            throw new BusinessException(401, "Provide Email !");
        }

        if (Objects.isNull(userRegisterDTO.getPassword())) {
            throw new BusinessException(401, "Provide Password !");
        }

        if (Objects.isNull(userRegisterDTO.getConfirmPassword())) {
            throw new BusinessException(401, "Confirm Password !");
        }

        if(!Objects.isNull(userRepository.findByEmail(userRegisterDTO.getEmail())))
        {
            throw new BusinessException(401,"This Email already in use !");
        }

        if(!userRegisterDTO.getConfirmPassword().equals(userRegisterDTO.getPassword()))
        {
            throw new BusinessException(403,"Passwords doesn't match !");
        }

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setRoles(roleRepository.findByRole("BASIC_USER"));

        return userRepository.save(user);
    }
}

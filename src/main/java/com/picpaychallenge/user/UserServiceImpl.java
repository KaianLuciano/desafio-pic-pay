package com.picpaychallenge.user;

import com.picpaychallenge.common.domain.config.exception.erros.ResourceNotFoundException;
import com.picpaychallenge.common.domain.config.exception.messageerror.MessageError;
import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;
import com.picpaychallenge.user.payload.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO create(UserForm userForm) {
        User user = new User(userForm);

        return new UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long idUser, UserForm userForm) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException(MessageError.USER_NOT_FOUND));
        user.update(userForm);
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO readById(Long idUser) {
        return userRepository.findById(idUser).map(UserDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(MessageError.USER_NOT_FOUND));
    }

    @Override
    public Page<UserDTO> readAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    @Override
    public void deleteUserById(Long idUser) {
        userRepository.deleteById(idUser);
    }
}

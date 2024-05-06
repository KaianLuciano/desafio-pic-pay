package com.picpaychallenge.user;

import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;
import com.picpaychallenge.user.payload.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user = userRepository.findById(idUser).orElseThrow();
        user.update(userForm);
        return new UserDTO(userRepository.save(user));
    }
}

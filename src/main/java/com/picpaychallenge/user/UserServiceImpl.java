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
    public UserDTO create(UserForm appointmentForm) {
        User user = new User(appointmentForm);

        return new UserDTO(userRepository.save(user));
    }
}

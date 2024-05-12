package com.picpaychallenge.user;

import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDTO create(UserForm userForm);
    UserDTO update(Long idUser, UserForm userForm);
    UserDTO readById(Long idUser);
    Page<UserDTO> readAll(Pageable pageable);
    void deleteUserById(Long idUser);
}

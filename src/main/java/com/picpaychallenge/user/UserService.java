package com.picpaychallenge.user;

import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;

import java.util.List;

public interface UserService {
    UserDTO create(UserForm userForm);
    UserDTO update(Long idUser, UserForm userForm);
    UserDTO readById(Long idUser);
    List<UserDTO> readAll();
}

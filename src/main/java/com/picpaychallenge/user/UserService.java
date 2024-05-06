package com.picpaychallenge.user;

import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;

import java.util.Collection;

public interface UserService {
    UserDTO create(UserForm userForm);
    UserDTO update(Long idUser, UserForm userForm);
}

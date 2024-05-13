package com.picpaychallenge.user.factory;

import com.picpaychallenge.common.domain.model.valueobjects.cpf.CPF;
import com.picpaychallenge.user.payload.TypeUser;
import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;

public class UserFactory {

    public static UserDTO getUserDTOUnitTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(1L);
        userDTO.setTypeUser(TypeUser.COMMON);
        userDTO.setDocument(new CPF("12345678901"));
        userDTO.setEmail("test@gmail.com");
        return userDTO;
    }

    public static UserDTO getUserDTOForPost() {
        UserDTO userDTO = new UserDTO();
        userDTO.setTypeUser(TypeUser.COMMON);
        userDTO.setDocument(new CPF("12345678901"));
        userDTO.setEmail("test@gmail.com");
        return userDTO;
    }

    public static UserDTO getUserDTOForPut() {
        UserDTO userDTO = new UserDTO();
        userDTO.setTypeUser(TypeUser.COMMON);
        userDTO.setDocument(new CPF("987654321"));
        userDTO.setEmail("testupdate@gmail.com");
        return userDTO;
    }

    public static UserForm getUserFormForPost() {
        UserForm userForm = new UserForm();
        userForm.setTypeUser(TypeUser.COMMON);
        userForm.setDocument(new CPF("12345678901"));
        userForm.setEmail("test@gmail.com");
        userForm.setPassword("senhaTeste123@");
        return userForm;
    }

    public static UserForm getUserFormForPut() {
        UserForm userForm = new UserForm();
        userForm.setTypeUser(TypeUser.COMMON);
        userForm.setDocument(new CPF("987654321"));
        userForm.setEmail("testupdate@gmail.com");
        userForm.setPassword("senhaUpdateTeste123@");
        return userForm;
    }

}

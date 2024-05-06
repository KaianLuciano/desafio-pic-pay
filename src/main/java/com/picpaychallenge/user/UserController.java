package com.picpaychallenge.user;

import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController("User Controller")
@RequestMapping("${picpay.challenge.base.path}/${picpay.challenge.base.version}/clinic-units")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Create new user", method = "POST")
    @PostMapping
    public ResponseEntity<UserDTO> postUser(@Valid @RequestBody UserForm userForm) {
        UserDTO userDTO = userService.create(userForm);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(String.valueOf(userDTO.getIdUser()))
                .build().toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }
}

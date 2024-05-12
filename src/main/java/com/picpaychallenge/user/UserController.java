package com.picpaychallenge.user;

import com.picpaychallenge.user.payload.UserDTO;
import com.picpaychallenge.user.payload.UserForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController("User Controller")
@RequestMapping("${picpay.challenge.base.path}/${picpay.challenge.base.version}/users")
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

    @Operation(summary = "Updates the user representing the given id", method = "PUT")
    @PutMapping("/{idUser}")
    public ResponseEntity<UserDTO> putUser(@PathVariable Long idUser, @Valid @RequestBody UserForm userForm) {
        return ResponseEntity.ok(userService.update(idUser, userForm));
    }

    @Operation(summary = "Return all users", method = "GET")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllClinicUnit() {
        List<UserDTO> userDTOS = userService.readAll();
        return ResponseEntity.ok(userDTOS);
    }

    @Operation(summary = "Return the user representing the given id", method = "GET")
    @GetMapping("/{idUser}")
    public ResponseEntity<UserDTO> getClinicUnit(@PathVariable Long idUser) {
        UserDTO userDTO = userService.readById(idUser);
        return ResponseEntity.ok(userDTO);
    }
}

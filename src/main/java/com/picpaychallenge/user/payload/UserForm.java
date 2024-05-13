package com.picpaychallenge.user.payload;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
    @NotNull
    private Document document;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private TypeUser typeUser;
}

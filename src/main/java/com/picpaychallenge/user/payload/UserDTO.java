package com.picpaychallenge.user.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import com.picpaychallenge.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Embedded
    @Column(unique = true)
    private Document document;
    @Email
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    public UserDTO(User user) {
        this.idUser = user.getIdUser();
        this.document = user.getDocument();
        this.email = user.getEmail();
        this.typeUser = user.getTypeUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return Objects.equals(getIdUser(), userDTO.getIdUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idUser", idUser)
                .append("document", document)
                .append("email", email)
                .append("typeUser", typeUser)
                .toString();
    }
}

package com.picpaychallenge.user;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import com.picpaychallenge.user.payload.TypeUser;
import com.picpaychallenge.user.payload.UserForm;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Type;
import java.util.Objects;


@Entity
@Table(name = "tb_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Embedded
    @Column(unique = true)
    private Document document;
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    public User(UserForm appointmentForm) {
        this.document = appointmentForm.getDocument();
        this.email = appointmentForm.getEmail();
        this.password = appointmentForm.getPassword();
        this.typeUser = appointmentForm.getTypeUser();
    }

    public void update(UserForm userForm) {
        this.document = userForm.getDocument();
        this.email = userForm.getEmail();
        this.password = userForm.getPassword();
        this.typeUser = userForm.getTypeUser();    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(idUser, user.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUser);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idUser", idUser)
                .append("document", document)
                .append("email", email)
                .append("password", password)
                .toString();
    }
}

package com.CmsShoppingCart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "users")
@Data
public class User {

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, message = "{user.register.username.length}")
    private String username;

    @NotNull
    @Size(min = 4, message = "{user.register.password.length}")
    @ToString.Exclude
    private String password;

    @Transient
    @ToString.Exclude
    private String passwordConfirm;

    @NotNull
    @Email(message = "{user.register.validEmail}")
    private String email;

    @NotNull
    @Size(min = 6, message = "{user.register.phoneNumber.length}")
    private String phoneNumber;

    @NotNull
    private int status;

    @Transient
    private LocalDateTime idate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date udate;
}

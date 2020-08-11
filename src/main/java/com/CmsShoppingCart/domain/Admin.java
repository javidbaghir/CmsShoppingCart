package com.CmsShoppingCart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Admin {

    private static final long serialVersionUID = 2l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(length = 45)
    @Size(min = 2, message = "{admin.register.username.length}")
    private String username;

    @NotNull
    @Size(min = 4, message = "{admin.register.password.length}")
    @ToString.Exclude
    private String password;

    @NotNull
    private int status;
}

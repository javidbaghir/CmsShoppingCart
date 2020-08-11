package com.CmsShoppingCart.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 2, message = "{product.name.length}")
    private String name;

    private String slug;

    @Lob
    @NotNull
    @Size(min = 2, message = "{product.description.length}")
    private String description;

    private String image;

    @Pattern(regexp = "^[0-9]+([.][0-9]{1,2})?", message = "{product.price}")
    private String price;

    @Pattern(regexp = "^[1-9][0-9]*", message = "{product.categoryId}")
    @NotNull
    private String categoryId;

    @NotNull
    private int status;

    @Transient
    private LocalDateTime idate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date udate;
}

package com.CmsShoppingCart.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, message = "{category.name.length}")
    private String name;

    @Size(min = 2, message = "{category.slug.length}")
    private String slug;

    private int sorting;

    private int status;

    @Transient
    private LocalDateTime idate;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date udate;


}

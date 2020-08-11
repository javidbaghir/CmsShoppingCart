package com.CmsShoppingCart.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
public class Pages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, message = "{page.title.length}")
    @NotNull
    private String title;

    @Size(min = 2, message = "{page.slug.length}")
    private String slug;

    @Size(min = 5, message = "{page.content.length}")
    private String content;

    private int sorting;

    private int status;

    @Transient
    private LocalDateTime idate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date udate;

}

package com.CmsShoppingCart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "cms")
public class CmsConfigration {

    private int pageSize;

}

package com.hugo.blog.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "article")
@Data
public class ArticleSettings {
    private  Integer  lowRead;
    private  Integer  topRead;
}

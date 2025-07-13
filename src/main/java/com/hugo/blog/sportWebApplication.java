package com.hugo.blog;

import com.hugo.blog.settings.ArticleSettings;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.hugo.blog.mapper")
@EnableConfigurationProperties({ArticleSettings.class})
public class sportWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(sportWebApplication.class, args);
	}

}

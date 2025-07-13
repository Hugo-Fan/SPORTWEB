package com.hugo.blog.settings;

import com.hugo.blog.fomatter.IdTypeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MvcSettings implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new IdTypeFormatter());
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/view/addArticle").setViewName("/blog/addArticle");
        registry.addViewController("/view/userlogin").setViewName("/blog/userLogin");
        registry.addViewController("/view/forgetpasword").setViewName("/blog/forgetpasword");
        registry.addViewController("/view/addUser").setViewName("/blog/addUser");
        registry.addViewController("/view/addSportList").setViewName("/blog/addSportList");
        registry.addViewController("/view/introduce").setViewName("/blog/introduce");
    }
}

package com.hugo.blog.model.param;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleParam {

    public static interface AddArticle{}
    public static interface EditArticle{}

    @NotNull(message = "修改時必須有id",groups = {EditArticle.class})
    @Min(value = 1 ,message = "文章id大於{value}",groups = {AddArticle.class})
    private Integer id; // 文章id

    @NotNull(message = "請輸入文章標題",groups = {AddArticle.class,EditArticle.class})
    @Size(min = 2,max = 20,message = "文章標題在{min}~{max}",groups = {AddArticle.class,EditArticle.class})
    private String title;


    @NotNull(message = "請輸入文章內容(大綱)",groups = {AddArticle.class,EditArticle.class})
    @Size(min = 20,max = 8000,message = "文章內容至少{min}，最多{max}間",groups = {AddArticle.class,EditArticle.class})
    private String content;



}


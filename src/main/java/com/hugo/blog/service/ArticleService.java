package com.hugo.blog.service;

import com.hugo.blog.model.dto.ArticleDTO;
import com.hugo.blog.model.dto.SportlistDTO;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.po.UsersPO;

import java.util.List;

/**
 *  article service
 *
 */
public interface ArticleService {

    // 獲取首頁文章列表
    List<ArticlePO> queryTopArticle(Integer page);
    // 查詢文章總數
    int articleSum();

    // 發布文章(article,article_detail)
    boolean addArticle(ArticleDTO articleDTO,UsersDTO usersDTO);

    // 根據主鍵查詢文章
    ArticleDTO queryByArticleId(Integer id);

    // 修改文章內容
    boolean modifyArticle(ArticleDTO articleDTO);
    // 刪除文章
    boolean removeArticle(List<Integer> idList);

    //查詢文章內容前20
    String queryTop20Content(Integer id);

}

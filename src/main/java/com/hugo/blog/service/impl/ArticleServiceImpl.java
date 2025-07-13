package com.hugo.blog.service.impl;

import com.hugo.blog.model.dto.ArticleDTO;
import com.hugo.blog.model.dto.SportlistDTO;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.map.ArticleAndDetailMap;
import com.hugo.blog.model.po.ArticleDetailPO;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.po.UsersPO;
import com.hugo.blog.settings.ArticleSettings;
import com.hugo.blog.mapper.ArticleMapper;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor // 等同構造注入
public class ArticleServiceImpl implements ArticleService {


    private final ArticleMapper articleMapper;
    private final ArticleSettings articleSettings;

    @Override
    public List<ArticlePO> queryTopArticle(Integer page) {
        log.info("執行queryTopArticle");
        Integer lowRead = articleSettings.getLowRead();
        Integer topRead = articleSettings.getTopRead();
        if(page !=0){
            page = page *10;
        }
        List<ArticlePO> articleList = articleMapper.topSortByReadCount(lowRead,page);
        return articleList;
    }

    // 查詢文章總數
    @Override
    public int articleSum() {
        log.info("執行articleSum");
        int i = articleMapper.articleSums();
        return i;
    }

    // 發布文章(article,article_detail)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addArticle(ArticleDTO articleDTO, UsersDTO usersDTO) {
        log.info("執行addArticle");
        //文章
        ArticlePO articlePO = new ArticlePO();
        articlePO.setTitle(articleDTO.getTitle());
        articlePO.setSummary(articleDTO.getSummary());
        articlePO.setCreateTime(LocalDateTime.now());
        articlePO.setUpdateTime(LocalDateTime.now());
        articlePO.setReadCount(new Random().nextInt(200));
        articlePO.setUserId(usersDTO.getUser_id());

        int addArticle = articleMapper.insertArticle(articlePO);

        //文章內容
        ArticleDetailPO articleDetailPO = new ArticleDetailPO();
        articleDetailPO.setArticleId(articlePO.getId());
        articleDetailPO.setContent(articleDTO.getContent());
        int addDetial = articleMapper.insertArticleDetail(articleDetailPO);

        return (addArticle + addDetial) == 2 ? true : false;
    }

    @Override
    public ArticleDTO queryByArticleId(Integer id) {
        log.info("執行queryByArticleId");
        //文章屬性，內容
        ArticleAndDetailMap mapper = articleMapper.selectArticleAndDetail(id);
        // 轉為DTO
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(mapper.getTitle());
        articleDTO.setSummary(mapper.getSummary());
        articleDTO.setContent(mapper.getContent());
        articleDTO.setId(mapper.getId());

        return articleDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyArticle(ArticleDTO articleDTO) {
        log.info("執行modifyArticle");
        //修改文章屬性
        ArticlePO articlePO = new ArticlePO();
        articlePO.setTitle(articleDTO.getTitle());
        articlePO.setSummary(articleDTO.getSummary());
        articlePO.setUpdateTime(LocalDateTime.now());
        articlePO.setId(articleDTO.getId());
        int article = articleMapper.updateArticle(articlePO);
        //修改文章內容
        ArticleDetailPO articleDetailPO = new ArticleDetailPO();
        articleDetailPO.setArticleId(articleDTO.getId());
        articleDetailPO.setContent(articleDTO.getContent());
        int detail = articleMapper.updateArticleDetail(articleDetailPO);
        return (article + detail) == 2 ? true : false;
    }

    // 刪除文章屬性和內容
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeArticle(List<Integer> idList) {
        log.info("執行removeArticle");
        int article = articleMapper.dedleteArticle(idList);
        int detail = articleMapper.deleteDetail(idList);
        return article == detail ? true : false;
    }


    @Override
    public String queryTop20Content(Integer id) {
        log.info("執行queryTop20Content");
        ArticleDetailPO articleDetailPO = articleMapper.selectDetailByArticleId(id);
        String content = "無內容";
        if (articleDetailPO != null) {
            content = articleDetailPO.getContent();

        }
        return content;
    }

}


package com.hugo.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hugo.blog.fomatter.IdType;
import com.hugo.blog.handler.exp.IdTypeException;
import com.hugo.blog.model.dto.ArticleDTO;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.param.ArticleParam;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.model.vo.ArticleVO;
import com.hugo.blog.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ArticleController {

    private final ArticleService articleService;

    // 首頁
    @GetMapping(value = {"/","/index"})
    public String index(HttpSession session ,Model model){
        if (session.getAttribute("userdata") != null) {
            UsersDTO usersDTO = (UsersDTO) session.getAttribute("userdata");
            model.addAttribute("userdatas",usersDTO);
            log.info(usersDTO.getUsername()+"執行首頁");
        }
        return "/blog/index";
    }

    // 文章首頁
    @GetMapping("/article/hot")
    public String showHotArticle(Model model, HttpSession session,@RequestParam(defaultValue = "0") Integer page) {
        int articleSum = articleService.articleSum();
        model.addAttribute("articleSum", articleSum);
        model.addAttribute("currentPage",page);
        List<ArticlePO> articlePOList = articleService.queryTopArticle(page);

        // 傳成VO類型 .hutool工具
        List<ArticleVO> articleVOList = BeanUtil.copyToList(articlePOList, ArticleVO.class);
        // 添加數據
        model.addAttribute("articleList", articleVOList);

        // 如果有帳號信息，將其放入Model中
        if (session.getAttribute("userdata") != null) {
            UsersDTO usersDTO = (UsersDTO) session.getAttribute("userdata");
            model.addAttribute("userdatas",usersDTO);
        }
        // 視圖
        return "/blog/articleList";

    }

    //發布新文章
    @PostMapping("/article/add")
    public String addArticle(@Validated(ArticleParam.AddArticle.class) ArticleParam param, HttpSession session) {
        // 應用service
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setContent(param.getContent());
        articleDTO.setTitle(param.getTitle());
        UsersDTO usersDTO = (UsersDTO) session.getAttribute("userdata");
        boolean add = articleService.addArticle(articleDTO, usersDTO);

        return "redirect:/article/hot";
    }

    //查詢文章內容
    @PostMapping ("/article/get")
    public String queryById(Integer id, Model model) {
        if (id != null && id > 0) {
            ArticleDTO articleDTO = articleService.queryByArticleId(id);
            // DTO轉VO
            ArticleVO articleVO = BeanUtil.copyProperties(articleDTO, ArticleVO.class);
            // 添加數據
            model.addAttribute("article", articleVO);
            // 視圖
            return "/blog/editArticle";
        } else {
            return "/blog/error/error";
        }
    }


    //更新文章
    @PostMapping("/article/edit")
    public String modifyArticle(@Validated(ArticleParam.EditArticle.class) ArticleParam param) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(param.getId());
        articleDTO.setTitle(param.getTitle());
        articleDTO.setContent(param.getContent());
        boolean edit = articleService.modifyArticle(articleDTO);
        return "redirect:/article/hot";
    }

    //刪除文章 ids= 1,6,2
    //public  String removeArticle(Integer ids[]){
    @PostMapping("/article/remove")
    public String removeArticle(@RequestParam("ids") IdType idType) {
        if (idType == null) {
            throw new IdTypeException("ID為空");
        } else {
            boolean delete = articleService.removeArticle(idType.getIdList());
            return "redirect:/article/hot";
        }
    }

    // 查看文章資料
    @GetMapping("/article/detail/overview")
    @ResponseBody
    public String queryDetail(Integer id) {
        String top20Content = "無ID";
        if (id != null && id > 0) {
            top20Content = articleService.queryTop20Content(id);
        }
        return top20Content;
    }
}

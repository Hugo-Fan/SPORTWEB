package com.hugo.blog.mapper;

import com.hugo.blog.model.map.ArticleAndDetailMap;
import com.hugo.blog.model.po.ArticleDetailPO;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.po.UsersPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArticleMapper {

    // 查詢首頁文章列表
    @Select("""
        select id,user_id ,title,summary,read_count,create_time,update_time
        from article
        where read_count >= #{lowRead}
        order by update_time desc
        limit #{page},10
    """)
    @Results(id="ArticleBaseMap" ,value = {
            @Result(id = true,column= "id" ,property = "id"),
            @Result(column= "user_id" ,property = "userId"),
            @Result(column= "title" ,property = "title"),
            @Result(column= "summary" ,property = "summary"),
            @Result(column= "read_count" ,property = "readCount"),
            @Result(column= "create_time" ,property = "createTime"),
            @Result(column= "update_time" ,property = "updateTime")
    })
    List<ArticlePO> topSortByReadCount(Integer lowRead , Integer page);

    //　查詢文章數量
    @Select("""
            select count(id) from article
            """)

    int articleSums();

    // 查詢運動紀錄數量

    @Select("""
            select count(*) from sportlist
            where user_id = #{user_id}
            """)

    int SportListSum(Integer user_id);

    // 添加article
    @Insert("""
            insert  into article(user_id ,title,summary,read_count,create_time,update_time)
            values(#{userId},#{title},#{summary},#{readCount},#{createTime},#{updateTime})
            """)
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int insertArticle(ArticlePO articlePO);

    //添加文章內容
    @Insert("""
            insert into article_detail(article_id,content)
            values(#{articleId},#{content})
            """)
    int insertArticleDetail(ArticleDetailPO articleDetailPO);



    //兩個表的連接
    @Select("""
            select m.id as aticle_id ,title,summary,ad.content
            from article m inner join article_detail ad
            on m.id = ad.article_id
            where m.id = #{id}
            """)
    @Results(id = "ArticleAndDetailMapper", value = {
            @Result(id = true, column = "aticle_id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "summary", property = "summary"),
            @Result(column = "content", property = "content")
    })
    ArticleAndDetailMap selectArticleAndDetail(Integer id);



    //修改文章屬性
    @Update("""
            update article set  title = #{title} , summary = #{summary} , update_time = #{updateTime}
            where id = #{id}
            """)
    int updateArticle(ArticlePO articlePO);
    //更新文章內容
    @Update("""
            update article_detail set content=#{content}
            where article_id = #{articleId}
            """)
    int updateArticleDetail(ArticleDetailPO articleDetailPO);


    // 刪除文章屬性
    @Delete("""
            <script>
                delete from article where id in
                <foreach item="id" collection="list" open="(" separator="," close=")">
                    #{id}
                </foreach>
            </script>
            """)
    int dedleteArticle(List<Integer> idList);

    //刪除文章內容
    @Delete("""
            <script>
                delete from article_detail where article_id in
                <foreach item="id" collection="list" open="(" separator="," close=")">
                #{id}
                </foreach>
            </script>
            """)
    int deleteDetail(List<Integer> idList);

    // 查詢文章內容前20字
    @Select("""
            select id,article_id,content from article_detail
            where article_id= #{id}
            """)
    @Results(id = "articleDatealMapper" ,value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "article_id",property = "articleId"),
            @Result(column = "content",property = "content")
    })
    ArticleDetailPO selectDetailByArticleId(Integer articleId);


}

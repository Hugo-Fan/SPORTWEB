package com.hugo.blog.mapper;

import com.hugo.blog.model.map.ArticleAndDetailMap;
import com.hugo.blog.model.po.ArticleDetailPO;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.po.UsersPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SportMapper {


    // 查詢運動紀錄數量
    @Select("""
            select count(*) from sportlist
            where user_id = #{user_id}
            """)

    int SportListSum(Integer user_id);


    // 依user_id查詢運動紀錄
    @Select("""
            select sport_id,user_id,date,sportlist,actiongroup
            from sportlist
            where user_id = #{user_id}
            order by date desc
            limit #{page},10
            """)
    @Results(id = "sports" ,value = {
            @Result(id = true,column = "sport_id",property = "sport_id"),
            @Result(column = "user_id",property = "user_id")

    })
    List<SportlistPO> SelectSportList(Integer user_id,Integer page);

    //新增運動紀錄
    @Insert("""
            insert into sportlist(user_id, date,sportlist,actiongroup)
            values(#{user_id},#{date},#{sportlist},#{actiongroup})
            """)
    // 回傳基本型態不用映射
//    @Results(id = "sports" ,value = {
//            @Result(id = true,column = "user_id",property = "user_id"),
//
//    })
    boolean addSports(SportlistPO sportlistPO);

    //依sport_id查詢sportData
    @Select("""
            select sport_id,user_id,date,sportlist,actiongroup
            from sportlist
            where sport_id = #{sport_id};
            """)
    @Results(id = "sportss" ,value = {
            @Result(id = true,column = "sport_id",property = "sport_id"),
            @Result(column = "user_id",property = "user_id")

    })
    SportlistPO SelectSportlistBysportid(Integer sport_id);

    //依sport_id和actionGroup查詢sportData
    @Select("""
            select sport_id,user_id,date,sportlist,actiongroup
            from sportlist
            where user_id = #{user_id} and actiongroup = #{actiongroup} 
            ORDER BY date DESC
            LIMIT 1;
            """)
    @Results(id = "actionandid" ,value = {
            @Result(id = true,column = "sport_id",property = "sport_id"),
            @Result(column = "user_id",property = "user_id")

    })
    SportlistPO SelectSportlistByaction(String actiongroup, Integer user_id);

    // upDateSportList
    @Update("""
            update sportlist set user_id = #{user_id}, date = #{date} , sportlist = #{sportlist} ,actiongroup = #{actiongroup}
            where sport_id = #{sport_id}
            """)
    // 回傳基本型態不用映射
//    @Results(id = "editsport" ,value = {
//            @Result(id = true,column = "sport_id",property = "sport_id"),
//            @Result(column = "user_id",property = "user_id")
//
//    })
    boolean editsport(SportlistPO sportlistPO);

    // 刪除sportData
    @Delete("""
            <script>
                delete from sportlist where sport_id in
                <foreach item="sport_id" collection="list" open="(" separator="," close=")">
                #{sport_id}
                </foreach>
            </script>
            """)
    boolean deletesport(List<Integer> idList);
}

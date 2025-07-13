package com.hugo.blog.service;

import com.hugo.blog.model.dto.SportlistDTO;
import com.hugo.blog.model.po.SportlistPO;

import java.util.List;

/**
 *  article service
 *
 */
public interface SportService {

    //依user_id查詢sportData
    List<SportlistPO> querySportList(Integer user_id, Integer page);

    //新增運動紀錄
    Boolean addSport(SportlistDTO sportlistDTO);

    //依sport_id查詢sportData
    SportlistDTO queryBySportId(Integer sport_id);

    // 查詢user_id有的運動紀錄總數
    Integer SportListSum(Integer user_id);

    // 修改運動紀錄
    Boolean editSportData(SportlistDTO sportlistDTO);

    // 單一查詢sportList
    String queryBySportList(Integer sport_id);

    // 使用動作組查詢sportList
    String queryBySportList(String actionGroup, Integer user_id);

    // 刪除sportData
    boolean removeSport(List<Integer> idList);
}

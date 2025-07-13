package com.hugo.blog.service.impl;

import com.hugo.blog.mapper.ArticleMapper;
import com.hugo.blog.mapper.SportMapper;
import com.hugo.blog.model.dto.ArticleDTO;
import com.hugo.blog.model.dto.SportlistDTO;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.map.ArticleAndDetailMap;
import com.hugo.blog.model.po.ArticleDetailPO;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.po.UsersPO;
import com.hugo.blog.service.ArticleService;
import com.hugo.blog.service.SportService;
import com.hugo.blog.settings.ArticleSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
@Slf4j
@Service
@RequiredArgsConstructor // 等同構造注入
public class SportServiceImpl implements SportService {

    private final SportMapper sportMapper;

    //依user_id查詢sportData
    @Override
    public List<SportlistPO> querySportList(Integer user_id, Integer page) {
        log.info(user_id+" 執行querySportList.service");
        if(page !=0){
            page = page *10;
        }
        List<SportlistPO> sportlistPOS = sportMapper.SelectSportList(user_id,page);
        return sportlistPOS;
    }

    //新增運動紀錄
    @Override
    public Boolean addSport(SportlistDTO sportlistDTO) {
        log.info(sportlistDTO.getUser_id()+" 執行addSport.service");
        SportlistPO sportlistPO = new SportlistPO();
        sportlistPO.setUser_id(sportlistDTO.getUser_id());
        sportlistPO.setDate(sportlistDTO.getDate());
        sportlistPO.setSportlist(sportlistDTO.getSportlist());
        sportlistPO.setActiongroup(sportlistDTO.getActiongroup());
        return sportMapper.addSports(sportlistPO);
    }

    //依sport_id查詢sportData
    @Override
    public SportlistDTO queryBySportId(Integer sport_id) {
        log.info(sport_id+" 執行queryBySportId.service");
        SportlistPO sportlistPO = sportMapper.SelectSportlistBysportid(sport_id);
        SportlistDTO sportlistDTO = new SportlistDTO();
        sportlistDTO.setSport_id(sportlistPO.getSport_id());
        sportlistDTO.setUser_id(sportlistPO.getUser_id());
        sportlistDTO.setDate(sportlistPO.getDate());
        sportlistDTO.setActiongroup(sportlistPO.getActiongroup());
        sportlistDTO.setSportlist(sportlistPO.getSportlist());
        return sportlistDTO;
    }

    // 查詢user_id有的運動紀錄總數
    @Override
    public Integer SportListSum(Integer user_id) {
        log.info(user_id+" 執行queryBySportId.service");
        Integer i = sportMapper.SportListSum(user_id);
        return i;
    }

    // 修改運動紀錄
    @Override
    public Boolean editSportData(SportlistDTO sportlistDTO) {
        log.info(sportlistDTO.getUser_id()+" 執行queryBySportId.service");
        SportlistPO sportlistPO = new SportlistPO();
        sportlistPO.setSport_id(sportlistDTO.getSport_id());
        sportlistPO.setUser_id(sportlistDTO.getUser_id());
        sportlistPO.setDate(sportlistDTO.getDate());
        sportlistPO.setSportlist(sportlistDTO.getSportlist());
        sportlistPO.setActiongroup(sportlistDTO.getActiongroup());
        return sportMapper.editsport(sportlistPO);
    }

    // 單一查詢sportList
    @Override
    public String queryBySportList(Integer sport_id) {
        log.info(sport_id+" 執行queryBySportList.service");
        SportlistPO sportlistPO = sportMapper.SelectSportlistBysportid(sport_id);
        return sportlistPO.getSportlist();
    }

    // 使用動作組查詢sportList
    @Override
    public String queryBySportList(String actionGroup, Integer user_id) {
        log.info(user_id+" 執行queryBySportList(動作組).service");
        SportlistPO sportlistPO = sportMapper.SelectSportlistByaction(actionGroup,user_id);
        if(sportlistPO == null){
           return "無資料";
        }
        String sportlog = sportlistPO.getSportlist();
        return sportlog;
    }

    // 刪除sportData
    @Override
    public boolean removeSport(List<Integer> idList) {
        log.info(" 執行removeSport.service");
        return sportMapper.deletesport(idList);
    }
}


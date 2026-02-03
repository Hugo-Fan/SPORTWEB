package com.hugo.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hugo.blog.fomatter.IdType;
import com.hugo.blog.handler.exp.IdTypeException;
import com.hugo.blog.model.dto.SportlistDTO;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.param.SportEditParam;
import com.hugo.blog.model.param.SportListParam;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.vo.SportlistVO;
import com.hugo.blog.service.SportService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SportController {
    @Autowired
    private  SportService sportService;

    // 運動首頁
    @GetMapping(value = {"/Sport/hot"})
    public String showSport(Model model, HttpSession session, @RequestParam(defaultValue = "0") Integer page) {
        // 如果有帳號信息，將其放入Model中
        if (session.getAttribute("userdata") != null) {
            UsersDTO usersDTO = (UsersDTO) session.getAttribute("userdata");
            int SportListSum  =sportService.SportListSum(usersDTO.getUser_id());
            model.addAttribute("SportListSum",SportListSum);
            model.addAttribute("currentPage",page);
            model.addAttribute("userdatas",usersDTO);

            List<SportlistPO> sportListPoList = sportService.querySportList(usersDTO.getUser_id(),page);
            List<SportlistVO> sportListVoList = BeanUtil.copyToList(sportListPoList,SportlistVO.class);
            model.addAttribute("SportData",sportListVoList);
            log.info(usersDTO.getUsername()+" 執行sport首頁");
        }
        // 視圖
        return "blog/sportList";

    }

    // 新增運動紀錄
    @PostMapping("/sport/addSportList")
    public String addSportList(@Validated SportListParam param, HttpSession session){
        log.info("執行addSportList");
        UsersDTO usersDTO = (UsersDTO) session.getAttribute("userdata");
        SportlistDTO sportlistDTO = new SportlistDTO();
        sportlistDTO.setActiongroup(param.getActionGroup());
        sportlistDTO.setUser_id(usersDTO.getUser_id());
        sportlistDTO.setDate(param.getTimeInput());
        sportlistDTO.setSportlist(param.getSumResult());
        boolean add = sportService.addSport(sportlistDTO);

        if(add = true){
            log.info(usersDTO.getUsername()+" 新增運動紀錄成功");
            return "redirect:/Sport/hot";
        }else {
            log.info(usersDTO.getUsername()+" 新增運動紀錄失敗");
            return "blog/error/error";
        }
    }

    //依sport_id查詢sportdata
    @PostMapping("/sport/getSportList")
    public String queryBySportId(@RequestParam Integer sport_id, Model model) {
        log.info("執行queryBySportId");
        if (sport_id != null && sport_id > 0) {
            SportlistDTO sportlistDTO = sportService.queryBySportId(sport_id);

            String[]  sportsArray = sportlistDTO.getSportlist().split(",");
            List<String> sportList = Arrays.asList(sportsArray);
            sportlistDTO.setSportlists(sportList);

            // DTO轉VO
            SportlistVO sportlistVO = BeanUtil.copyProperties(sportlistDTO,SportlistVO.class);
            // 添加數據
            model.addAttribute("sportlist", sportlistVO);
            // 視圖
            log.info(sport_id+" 運動資料取得成功");
            return "blog/editsport";
        } else {
            log.info(sport_id+" 運動資料取得失敗");
            return "blog/error/error";
        }
    }

    // 查看sportList
    @GetMapping("/sport/detail/overviewSport")
    @ResponseBody
    public String querySportList(@RequestParam Integer sportId) {
        log.info("執行querySportList");
        String sportList = "無資料";
        if (sportId != null && sportId > 0) {
            sportList = sportService.queryBySportList(sportId);
            log.info("運動資料取得成功");
        }
        return sportList;
    }

    // addSport頁面查看最新的sportList
    @GetMapping("/sport/detail/addSportList")
    @ResponseBody
    public String addWebCheckSportList(@RequestParam String actionGroup, HttpSession session) {
        log.info("執行addWebCheckSportList");
        String sportList = "無資料";
        if (actionGroup != null && session.getAttribute("userdata" ) !=null) {
            UsersDTO usersDTO = (UsersDTO) session.getAttribute("userdata");
            sportList = sportService.queryBySportList(actionGroup,usersDTO.getUser_id());
            log.info("最新運動取得成功");
        }
        return sportList;
    }

    //刪除運動紀錄 spids= 1,6,2
    //public  String removeArticle(Integer ids[]){
    @PostMapping("/sport/removeSport")
    public String removeSport(@RequestParam("spIds") IdType idType) {
        log.info("執行removeSport");
        if (idType == null) {
            throw new IdTypeException("ID為空");
        } else {
            boolean delete = sportService.removeSport(idType.getIdList());
            log.info("運動紀錄刪除成功");
            return "redirect:/Sport/hot";
        }
    }

    //更新運動資料
    @PostMapping("/sport/editSport")
    public String editSport(@Validated SportEditParam param) {
        log.info("執行editSport");
        SportlistDTO sportlistDTO = new SportlistDTO();
        sportlistDTO.setSport_id(param.getSport_id());
        sportlistDTO.setUser_id(param.getUser_id());
        sportlistDTO.setDate(param.getDate());
        sportlistDTO.setSportlist(param.getSports());
        sportlistDTO.setActiongroup(param.getActionGroup());
        
        Boolean edit = sportService.editSportData(sportlistDTO);
        if(edit == true){
            log.info("更新運動紀錄成功");
            return "redirect:/Sport/hot";
        }else {
            log.info("更新運動紀錄失敗");
            return "blog/error/error";
        }
    }
}

package com.example.controller;


import com.example.Util;
import com.example.dao.CommentDao;
import com.example.dao.busRepository;
import com.example.domain.Game;
import com.example.domain.ban_user_com;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
public class busController {
    @Autowired
    private busRepository busRepository;


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/add")
    public String add(@RequestParam("uid") int uid,
                      @RequestParam("gid") int gid){
        List<ban_user_com> all_g = busRepository.findAll();
        int num = 0;
        for(int i = 0; i < all_g.size(); i++){
            if(all_g.get(i).getKeyid() > num){
                num = all_g.get(i).getKeyid();
            }
        }
        num++;
        ban_user_com bus = new ban_user_com();
        bus.setGid(gid);
        bus.setUid(uid);
        bus.setKeyid(num);
        busRepository.saveAndFlush(bus);

        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/judgeByUidAndGid")
    public String judge(@RequestParam("uid") int uid,
                      @RequestParam("gid") int gid){
        List<ban_user_com> ge = busRepository.findByUidAndGid(uid, gid);
        if(ge.size() > 0){
            return "0";
        }else {
            return "1";
        }
    }


}

package com.example.controller;

import com.example.dao.Game_Dlc_PathRepository;
import com.example.dao.UserDao;
import com.example.dao.ugdDao;
import com.example.domain.User;
import com.example.domain.User_game_dlc;
import com.example.domain.dev_game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ugd")
public class UgdController {
    @Autowired
    private ugdDao dao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Game_Dlc_PathRepository game_dlc_pathRepository;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findBygidByuid")
    public int findBygidByuid(@RequestParam int uid,
                                @RequestParam int gid,
                              @RequestParam int dlcid){
        if(dao.findByUidAndGidAndDlc(uid,gid,dlcid).size() > 0){
            return 1;
        }return 0;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/buyByuidBygid")
    public int buyBygidByuid(@RequestParam int uid,
                             @RequestParam int gid,
                             @RequestParam int dlcid){
        User_game_dlc user_game_dlc = new User_game_dlc();
        user_game_dlc.setUid(uid);
        user_game_dlc.setGid(gid);
        user_game_dlc.setDlc(dlcid);
        dao.saveAndFlush(user_game_dlc);

        User get_a = userDao.findUserByUid(uid).get(0);
        get_a.setBalance(Double.parseDouble(String.format("%.2f", (get_a.getBalance()-
                game_dlc_pathRepository.findByGidAndDlcid(gid,dlcid).get(0).getPrice()))));
        userDao.saveAndFlush(get_a);


        User get_u = userDao.findUserByUid(game_dlc_pathRepository.findByGidAndDlcid(gid,dlcid).get(0).getDid()).get(0);

        get_u.setBalance(Double.parseDouble(String.format("%.2f", (get_u.getBalance()+
                game_dlc_pathRepository.findByGidAndDlcid(gid,dlcid).get(0).getPrice()))));
        userDao.saveAndFlush(get_u);
        return 1;
    }

}

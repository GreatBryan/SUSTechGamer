package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Util;
import com.example.dao.Dev_GameRepository;
import com.example.dao.GameRepository;
import com.example.dao.UserDao;
import com.example.domain.Game;
import com.example.domain.User;
import com.example.domain.dev_game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("dev_game")
public class Dev_GameController {

    @Autowired
    private Dev_GameRepository dev_gameRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Util util;

    @ResponseBody
    @RequestMapping("/getAll")
    public List<dev_game> findAll(){
        return dev_gameRepository.findAll();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findByUid")
    public JSONArray findByUid(@RequestParam int uid){
        JSONArray json = new JSONArray();
        List<dev_game> ge = dev_gameRepository.findByUid(uid);
        for(int i = 0; i < ge.size(); i++){
            JSONObject jk = new JSONObject();
            jk.put("gid", ge.get(i).getGid());
            jk.put("name", gameRepository.findById(ge.get(i).getGid()).get(0).getName());
            json.add(jk);
        }
        return json;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findBygidByuid")
    public int findByName(@RequestParam int uid,
                             @RequestParam int gid){
        if(dev_gameRepository.findByUidAndGid(uid,gid).size() > 0){
            return 1;
        }return 0;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/buyByuidBygid")
    public int buyBygidByuid(@RequestParam int uid,
                          @RequestParam int gid){
        dev_game dev_game = new dev_game();
        dev_game.setDlc_num(0);
        dev_game.setGid(gid);
        dev_game.setUid(uid);
        dev_game.setKeyid(dev_gameRepository.findAll().size()+1);
        dev_gameRepository.saveAndFlush(dev_game);
//        userDao.updateBalanceByID(uid,
//                Double.parseDouble(
//                        String.format("%.2f", userDao.findUserByUid(uid).get(0).getBalance()-
//                        gameRepository.findById(gid).get(0).getPrice())));
        double dis = 1;
        try {
            if (gameRepository.findById(gid).get(0).getEnd_time().after(new Date())) {
                dis = gameRepository.findById(gid).get(0).getDiscount();
            }
        }catch (NullPointerException e){

        }

        User get_a = userDao.findUserByUid(uid).get(0);
        get_a.setBalance(Double.parseDouble(String.format("%.2f", (get_a.getBalance()-
                gameRepository.findById(gid).get(0).getPrice() * dis))));
        userDao.saveAndFlush(get_a);

        User get_u = userDao.findUserByUid(gameRepository.findById(gid).get(0).getUid()).get(0);
        get_u.setBalance(Double.parseDouble(String.format("%.2f",
                (get_u.getBalance()+gameRepository.findById(gid).get(0).
                        getPrice() * dis))));
        userDao.saveAndFlush(get_u);
        return 1;
    }


}

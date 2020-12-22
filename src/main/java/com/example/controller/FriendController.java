package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.FriendDao;
import com.example.dao.UserDao;
import com.example.domain.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private FriendDao dao;
    @Autowired
    private UserDao userDao;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/addFriend")
    public String addFriend(@RequestParam("user1") int u1,
                            @RequestParam("user2") int u2){
        int small = (u1<u2)?u1:u2;
        int big = (u1<u2)?u2:u1;
        System.out.println(small);
        System.out.println(big);
        Friend toAdd = new Friend();
        toAdd.setUser1(small);
        toAdd.setUser2(big);
        dao.saveAndFlush(toAdd);

        Friend toAdd_1 = new Friend();
        toAdd_1.setUser1(big);
        toAdd_1.setUser2(small);
        dao.saveAndFlush(toAdd_1);
        return "1";
    }

    @CrossOrigin
    @Transactional
    @ResponseBody
    @GetMapping("/deleteFriend")
    public String deleteFriend(@RequestParam("user1") int u1,
                               @RequestParam("user2") int u2){
        int small = (u1<u2)?u1:u2;
        int big = (u1<u2)?u2:u1;
        dao.deleteByUser1AndUser2(small,big);
        dao.deleteByUser1AndUser2(big,small);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/changeIntimacy")
    public String changeIntimacy(@RequestParam("value") int val,
                                 @RequestParam("user1") int u1,
                                 @RequestParam("user2") int u2){
        int small = (u1<u2)?u1:u2;
        int big = (u1<u2)?u2:u1;
        dao.changeIntimacy(val,small,big);
        dao.changeIntimacy(val,big,small);
        //TODO: add some boundaries for relationship
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/getFriends")
    public JSONArray getFriends(@RequestParam("user1") int uid){
        JSONArray json = new JSONArray();
        List<Friend> friends = dao.findByUser1(uid);
        for (int i = 0; i < friends.size(); i++) {
            JSONObject js = new JSONObject();
            js.put("uid", friends.get(i).getUser2());
            js.put("name", userDao.findUserByUid(friends.get(i).getUser2()).get(0).getName());
            File f = new File("D:/Intellij/game/src/main/resources/static/user/"+friends.get(i).getUser2()+"/profile.txt");
            try {
                String url = "";
                FileReader in = new FileReader(f);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                url = br.readLine();
                js.put("description", url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            json.add(js);
        }
        return json;
    }
}

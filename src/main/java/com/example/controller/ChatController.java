package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dao.ChatDao;
import com.example.dao.UserDao;
import com.example.domain.Chat;
//import com.example.domain.ChatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatDao chatDao;
    @Autowired
    private UserDao userDao;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/addChat")
    public String addChat(@RequestParam("sender") int sender,
                          @RequestParam("recv") int recv,
                          @RequestParam("content") String content){
        int chid = chatDao.findAll().size() + 1;
        Chat chat = new Chat();
        chat.setChatId(chid);
        chat.setSender(sender);
        chat.setRecv(recv);

        String[] temp = content.split("%20");
        String last = "";
        for(String str:temp){
            last = last +  str + " ";
        }
        temp = last.split("%0A");
        last = "";
        for(String str:temp){
            last = last +  str + "\n";
        }

        chat.setContent(last);
        chat.setSendDate(new Date());
        chatDao.saveAndFlush(chat);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getChat")
    public JSONArray getChat(@RequestParam("uid") int uid){
        JSONArray json = new JSONArray();
        List<Chat> ge = chatDao.findAllByRecv(uid);
        for(Chat ct : ge){
            JSONObject tmp = new JSONObject();
            tmp.put("sender", ct.getSender());
            tmp.put("name", userDao.findUserByUid(ct.getSender()).get(0).getName());
            tmp.put("date", ct.getSendDate().toString().split(" ")[0]);
            tmp.put("content", ct.getContent());
            json.add(tmp);
        }
        return json;
    }
}

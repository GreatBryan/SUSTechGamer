package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("SUSTechGamer")
public class WebController {
    @CrossOrigin
    @RequestMapping("About.html")
    public String about(){
        return "About";
    }

    @CrossOrigin
    @RequestMapping("administrator.html")
    public String administrator(){
        return "administrator";
    }

    @CrossOrigin
    @RequestMapping("Community.html")
    public String Community(){
        return "Community";
    }

    @CrossOrigin
    @RequestMapping("developerGame.html")
    public String developerGame(){
        return "developerGame";
    }

    @CrossOrigin
    @RequestMapping("friend.html")
    public String friend(){
        return "friend";
    }

    @CrossOrigin
    @RequestMapping("gamePage.html")
    public String gamePage(){
        return "gamePage";
    }

    @CrossOrigin
    @RequestMapping("login.html")
    public String log(){
        return "login";
    }

    @CrossOrigin
    @RequestMapping("message.html")
    public String message(){
        return "message";
    }

    @CrossOrigin
    @RequestMapping("register.html")
    public String register(){
        return "register";
    }

    @CrossOrigin
    @RequestMapping("Search.html")
    public String Search(){
        return "Search";
    }

    @CrossOrigin
    @RequestMapping("Store.html")
    public String Store(){
        return "Store";
    }

    @CrossOrigin
    @RequestMapping("userPage.html")
    public String userPage(){
        return "userPage";
    }




}

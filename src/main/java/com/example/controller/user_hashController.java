package com.example.controller;


import com.example.dao.user_hashRepository;
import com.example.domain.user_hash;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("userhash")
public class user_hashController {
    @Autowired
    user_hashRepository user_hashRepository;


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findHashByUid")
    public String findHashByUid(@RequestParam(name = "uid") int uid){
        return user_hashRepository.findByUid(uid).get(0).getHash();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findUidByHash")
    public String findUidByHash(@RequestParam(name = "hash") String hash){
        return user_hashRepository.findByHash(hash).get(0).getUid() + "";
    }



    @CrossOrigin
    @ResponseBody
    @RequestMapping("/hashUser")
    public String hashUser(@RequestParam(name = "uid") int uid){
        user_hash u_h = new user_hash();
        u_h.setUid(uid);
        u_h.setHash(encrypt(uid));
        user_hashRepository.saveAndFlush(u_h);
        return "1";
    }

    public static String encrypt(int num){
        String re = "";
        java.util.Random random=new java.util.Random();
        for(int i = 0; i < 8; i++){
            int ran = random.nextInt(10);
            re = re + num * ran % 10;
            switch (ran){
                case 1:
                    re += "h";
                    break;
                case 2:
                    re += "k";
                case 3:
                    re += "g";
                    break;
                case 4:
                    re += "b";
                case 5:
                    re += "p";
                    break;
                case 6:
                    re += "q";
                case 7:
                    re += "f";
                    break;
                case 8:
                    re += "y";
                case 9:
                    re += "t";
                    break;
                default:
                    re += "m";
            }
        }
        return re;
    }
}

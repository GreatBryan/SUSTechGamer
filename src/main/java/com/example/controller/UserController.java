package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Util;
import com.example.dao.UserDao;
import com.example.dao.user_hashRepository;
import com.example.domain.User;

import com.example.domain.UserInfo;
import com.example.domain.user_hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Util util;
    @Autowired
    private JavaMailSender sender;
    @Autowired
    private user_hashRepository user_hashRepository;

    @Value("D:/Intellij/game/src/main/resources/static/user/")
    private String userDir;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findAll")
    public String showAll(){
        StringBuilder sb = new StringBuilder();
        List<User> result = userDao.findAll();
        for(User item:result){
            sb.append(item.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/charge")
    public String charge(@RequestParam int uid,
                         @RequestParam int money){
        User u = userDao.findUserByUid(uid).get(0);
        u.setBalance(u.getBalance() + money);
        userDao.saveAndFlush(u);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/changePass")
    public String changePass(@RequestParam int uid,
                             @RequestParam String old_pass,
                             @RequestParam String new_pass){
        User g_u = userDao.findAllByUid(uid).get(0);
        if(g_u.getPassword().equals(old_pass)){
            g_u.setPassword(new_pass);
            userDao.saveAndFlush(g_u);
        }else {
            return "0";
        }
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/judgePass")
    public String judgePass(@RequestParam int uid,
                            @RequestParam String pass,
                            @RequestParam int money){
        User g_u = userDao.findAllByUid(uid).get(0);
        if(g_u.getPassword().equals(pass)){
            User u = userDao.findUserByUid(uid).get(0);
            u.setBalance(u.getBalance() + money);
            userDao.saveAndFlush(u);
            return "1";
        }else {
            return "0";
        }
    }



    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findByUid")
    public List<User> findbyuid(int uid){
        return userDao.findUserByUid(uid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findNameByUid")
    public String findNameByUid(@RequestParam("uid") int uid){
        return userDao.findUserByUid(uid).get(0).getName();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findBalanceByUid")
    public String findBalanceByUid(@RequestParam("uid") int uid){
        return userDao.findUserByUid(uid).get(0).getBalance() + "";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findPathByUid")
    public String findPathByUid(int uid){
        return userDao.findUserByUid(uid).get(0).getProfile();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findByName")
    public String findByName(String name){
        return userDao.findByName(name).toString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/findByUID")
    public String findByUID(@RequestParam("uid") int uid){return userDao.findAllByUid(uid).get(0).getName()+"";}

    public UserInfo getInfo(User user) throws IOException {
        String path = userDir + user.getIdUser() +"/profile.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String intro = br.readLine();
        String email = br.readLine();
        String birth = br.readLine();
        br.close();
        UserInfo info = new UserInfo(user.getIdUser(),user.getName(),user.getBalance(),user.getRole(),user.getSex(),intro,birth);
        return info;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/getFullInfo")
    public String getFullInfo(@RequestParam("uid") int uid) throws IOException {
        User user = userDao.findAllByUid(uid).get(0);
        return JSONObject.toJSONString(getInfo(user));
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/addItem")
    public String addItem(@RequestParam("id") Integer id, @RequestParam("name") String name){
        User item = new User();
        item.setIdUser(id);
        item.setName(name);
        userDao.saveAndFlush(item);
        return "Success added: " + item.toString();
    }

    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping("/updatePassword")
    public String changePassword(@RequestParam("id") Integer id, @RequestParam("password") String pass){
        userDao.updatePasswordByID(id,pass);
        return "Success";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password){
        List<User> result = userDao.findUsersByNameAndPassword(name,password);
        if (result.size() == 0)
            return "Fail";
        return result.get(0).getIdUser() + "";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/email")
    public String sendEmail(@RequestParam("email") String email){
        SimpleMailMessage msg = new SimpleMailMessage();
        System.out.println(email);
        msg.setFrom("11813015@mail.sustech.edu.cn");
        msg.setTo(email);
        msg.setSubject("Please verify your sign in for SUSGamer");
        String email_msg = "Your verification code is ";
        int code = 0;
        for (int i = 0; i < 6; i++) {
            int digit = (int)(Math.random()*10);
            if (digit == 10) digit = 9;
            if (digit == 0 && i == 5) digit = 1;
            for (int j = 0; j < i; j++) {
                digit *= 10;
            }
            code += digit;
        }
        email_msg += (code+".");

        msg.setText(email_msg);
        try {
            sender.send(msg);
        }catch (MailException ex){
            System.err.println(ex.getMessage());
            return "0";
        }
        return code+"";
    }

    //0: admin    1: user   2: developer
    //sex: 男: 大写M 女: 大写W
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/signup")
    public String signup(@RequestParam("uname") String name,
                         @RequestParam("password") String password,
                         @RequestParam("role") int rol,
                         @RequestParam("sex") char sex,
                         @RequestParam("email") String email) throws IOException {
        List<User> dup = userDao.findAllByName(name);
        if (dup.size() != 0)
            return "Duplicate";
        List<User> all_user = userDao.findAll();
        int num = all_user.size() + 1;
        String pathname = userDir + num + "";
        File file = new File(pathname);
        file.mkdirs();
        BufferedWriter bw = new BufferedWriter(new FileWriter(pathname+"/profile.txt"));

        bw.write("这个人很懒，还没有介绍自己");
        bw.write("\n");
        bw.write(email);
        bw.write("\n");
        bw.close();
        User newUser = new User(num,name,password,pathname+"/profile.txt",0,rol,sex);
        userDao.saveAndFlush(newUser);

        user_hash u_h = new user_hash();
        u_h.setUid(num);
        u_h.setHash(user_hashController.encrypt(num));
        user_hashRepository.saveAndFlush(u_h);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/picture")
    public String getPic(@RequestParam("picture") MultipartFile[] files,
                         @RequestParam("uid") int uid,
                         @RequestParam("gid") int gid){
        String pathname = "/Users/xuchengqi/Desktop";
        String f_name = "1.xml";
        util.httpUpload(files,pathname,f_name);
        return "1";
    }

    private List<String> getProfile(int id) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File(userDir+ id + "/profile.txt")));
        List<String> profile = new ArrayList<>();
        String line = "";
        while ((line = br.readLine()) != null){
            profile.add(line);
        }
        br.close();
        return profile;
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAvatar")
    public String getAvatar(@RequestParam("id") int id) throws IOException{
        List<String> profile = getProfile(id);
        String pathname = "User/" + id +"/" + profile.get(3);
        System.out.println(pathname);
        return pathname;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/changeAvatar")
    public String changeAvatar(@RequestParam("id") int id,
                               @RequestParam("avatar") MultipartFile[] files) throws IOException{
        String pathname =  userDir + id +"";
//        System.out.println(files[0].getOriginalFilename());
        String f_name = "photo."+files[0].getOriginalFilename().split("\\.")[1];
        util.httpUpload(files,pathname,f_name);
        return "1";
    }

    private void writeProfile(@RequestParam("id") int id, List<String> profile) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(userDir+ id + "/profile.txt"));
        for(String s: profile){
            bw.write(s);
            bw.write("\n");
        }
        bw.close();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/changeInfo")
    public String change(@RequestParam(value = "uid") int uid,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "sex", required = false, defaultValue = "a") char sex,
                         @RequestParam(value = "role", required = false, defaultValue = "9") int role,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "birth", required = false) String birth,
                         @RequestParam(value = "description", required = false) String descrption) throws IOException{
        if (name != null) userDao.updateNameByID(name,uid);
        if (sex != 'a') userDao.updateSexByID(sex,uid);
        if (role != 9) userDao.updateRoleByID(role,uid);
        List<String> profile = getProfile(uid);
        boolean change = false;
        if (email != null) {
            profile.remove(1);
            profile.add(1,email);
            change = true;
        }
        if (birth != null){
            try {
                profile.remove(2);
                profile.add(2,birth);
            }catch (IndexOutOfBoundsException e){
                profile.add(2,birth);
            }
            change = true;
        }
        if (descrption != null){
            profile.remove(0);
            profile.add(0,descrption);
            change = true;
        }

        if (change) writeProfile(uid,profile);

        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/changeEmail")
    public String changeEmail(@RequestParam("id") int id,
                              @RequestParam("email") String email)throws IOException{

        String filename = userDir + id + "/" + "profile.txt";
        File file = new File(filename);
        String line = util.getDes(filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(line);
        bw.write("\n");
        bw.write(email);
        bw.close();
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/changeDescription")
    public String changeDes(@RequestParam("id") int id,
                            @RequestParam("des") String des) throws IOException{
        String filename = userDir + id + "/" + "profile.txt";
        File file = new File(filename);
        String line = util.getEmail(filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(des);
        bw.write("\n");
        bw.write(line);
        bw.close();
        return "1";
    }
}

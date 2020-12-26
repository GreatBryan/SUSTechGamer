package com.example.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Util;
import com.example.dao.*;
import com.example.domain.User;
import com.example.domain.postbar;
import com.mysql.cj.xdevapi.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministerController {
    @Autowired
    private Util util;
    @Autowired
    private UserDao userDao;
    @Autowired
    private busRepository busRepository;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private PostbarRepository postbarRepository;
    @Autowired
    private Label_GameRepository label_gameRepository;
    @Autowired
    private Dev_GameRepository dev_gameRepository;
    @Autowired
    private ugdDao ugdDao;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private Game_Dlc_PathRepository game_dlc_pathRepository;


    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping("/deleteByGid")
    public String deleteByGid(@RequestParam("gid") int gid){
        busRepository.deleteAllByGid(gid);
        List<postbar> ge = postbarRepository.findByGid(gid);
        for(postbar g : ge){
            commentDao.deleteAllByTiezi(g.getTid());
        }
        dev_gameRepository.deleteAllByGid(gid);

        label_gameRepository.deleteAllById(gid);
        postbarRepository.deleteAllByGid(gid);
        ugdDao.deleteAllByGid(gid);
        deleteFolders("D:\\Intellij\\game\\src\\main\\resources\\static\\game\\"+gid+"game");
        game_dlc_pathRepository.deleteAllByGid(gid);
        gameRepository.deleteById(gid);
        return "1";
    }

    public boolean deleteFolders(String filePath){
        boolean isDeleteSuccess = false;
        LinkedList<String> folderList = new LinkedList<String>();
        folderList.add(filePath);

        while(folderList.size() > 0){
            File file1 = new File(folderList.poll());
            File[] files1 = file1.listFiles();
            ArrayList<File> fileList = new ArrayList<File>();
            for(int i = 0; i < fileList.size(); i++){
                if(files1[i].isDirectory()){
                    folderList.add(files1[i].getPath());
                }else{
                    fileList.add(files1[i]);
                }
            }
            //删除文件
            for(File file : fileList){
                file.delete();
            }
        }
        //删除文件夹
        folderList = new LinkedList<String>();
        folderList.add(filePath);
        while(folderList.size() > 0){
            File file2 = new File(folderList.getLast());
            if(file2.delete()){
                folderList.removeLast();
            }else{
                try {
                    File[] files2 = file2.listFiles();
                    for (int i = 0; i < files2.length; i++) {
                        folderList.add(files2[i].getPath());
                    }
                }catch (NullPointerException e){

                }
            }
        }
        if(folderList.size() == 0) isDeleteSuccess = true;
        return isDeleteSuccess;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllByUid")
    public JSONArray getAllByUid(@RequestParam("uid") int uid){
        JSONArray re = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("uid", uid);
        json.put("name", userDao.findUserByUid(uid).get(0).getName());
        json.put("balance", userDao.findUserByUid(uid).get(0).getBalance());
        json.put("role", userDao.findUserByUid(uid).get(0).getRole());
        json.put("sex", (userDao.findUserByUid(uid).get(0).getSex() + "").toLowerCase());

        File profile = new File("D:/Intellij/game/src/main/resources/static/user/" + uid + "/profile.txt");
        try {
            FileReader in = new FileReader(profile);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(profile)));
            for(int i = 0; i < 3; i++){
                String url = br.readLine();
                if (url != null) {
                    if(i == 0){
                        json.put("description", url);
                    }else if(i == 1){
                        json.put("email", url);
                    }else if(i == 2){
                        json.put("birth", url);
                    }
                } else{
                    if(i == 2){
                        json.put("birth", "");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        re.add(json);
        return re;
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllByName")
    public JSONArray getAllByName(@RequestParam("name") String name){
        JSONArray json = new JSONArray();
        List<User> all_user = userDao.findAll();
        for(User u : all_user) {
            if(u.getName().contains(name)) {
                int uid = u.getIdUser();
                JSONObject t1 = new JSONObject();
                t1.put("uid", uid);
                t1.put("name", userDao.findUserByUid(uid).get(0).getName());
                t1.put("balance", userDao.findUserByUid(uid).get(0).getBalance());
                t1.put("role", userDao.findUserByUid(uid).get(0).getRole());
                t1.put("sex", userDao.findUserByUid(uid).get(0).getSex());
                File profile = new File("D:/Intellij/game/src/main/resources/static/user/" + uid + "/profile.txt");
                try {
                    FileReader in = new FileReader(profile);
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(profile)));
                    for (int i = 0; i < 3; i++) {
                        String url = br.readLine();
                        if (url != null) {
                            if (i == 0) {
                                t1.put("description", url);
                            } else if (i == 1) {
                                t1.put("email", url);
                            } else if (i == 2) {
                                t1.put("birth", url);
                            }
                        } else {
                            if (i == 2) {
                                t1.put("birth", "");
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                json.add(t1);
            }
        }
        return json;
    }





}

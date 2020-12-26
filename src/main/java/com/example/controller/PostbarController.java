package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Util;
import com.example.dao.PostbarRepository;

import com.example.dao.UserDao;
import com.example.domain.Game;
import com.example.domain.postbar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("postbar")
public class PostbarController {
    @Autowired
    private PostbarRepository postbarRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Util util;

    //TODO: add @CrossOrigin
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllpostbar")
    public List<postbar> findAll() {
        return postbarRepository.findAll();
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findByTid")
    public List<postbar> findByTid(int tid) {
        return postbarRepository.findByTid(tid);
    }

    @ResponseBody
    @RequestMapping("/findByGid")
    public List<postbar> findByGid(int gid) {
        return postbarRepository.findByGid(gid);
    }

    @ResponseBody
    @RequestMapping("/findByUid")
    public List<postbar> findByUid(int uid) {
        return postbarRepository.findByUid(uid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/infoByGid")
    public JSONArray infoByGid(@RequestParam("gid") String gid) {
        List<postbar> get_postbar = postbarRepository.findByGid(Integer.parseInt(gid));
        JSONArray json = new JSONArray();
        for(postbar po : get_postbar){
            JSONObject jo = new JSONObject();
            jo.put("tid", po.getTid());
            jo.put("uid",po.getUid());
            jo.put("name", userDao.findUserByUid(po.getUid()).get(0).getName());
            jo.put("user_picture", "http://36058s3d36.zicp.vip/static/user/" + po.getUid() + "/photo.jpg");
            jo.put("content", po.getContent());
            jo.put("points", po.getPoints());
            jo.put("picture_path", po.getPicture());
            json.add(jo);
        }
        return json;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllbyTid")
    public JSONObject getAllbyTid(@RequestParam("tid") int tid){
        JSONObject json = new JSONObject();
        postbar po = postbarRepository.findByTid(tid).get(0);
        json.put("content", po.getContent());
        json.put("uid", po.getUid());
        json.put("name", userDao.findUserByUid(po.getUid())
                .get(0).getName());
        json.put("create_time", po.getCreate_time());
        json.put("points", po.getPoints());
        json.put("good", po.getZan());
        json.put("bad", po.getCai());
        json.put("path", po.getPicture() == null ? "" : po.getPicture());
        json.put("isvideo",po.getIsvideo());
        return json;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllByGid")
    public JSONArray getAllByGid(@RequestParam("gid") int gid){
        List<postbar> g_p = postbarRepository.findByGid(gid);
        JSONArray json = new JSONArray();
        for(postbar po : g_p){
            JSONObject jo = new JSONObject();
            jo.put("gid", gid);
            jo.put("tid", po.getTid());
            jo.put("content", po.getContent());
            jo.put("uid", po.getUid());
            jo.put("create_time", po.getCreate_time());
            jo.put("points", po.getPoints());
            jo.put("good", po.getZan());
            jo.put("bad", po.getCai());
            json.add(jo);
        }
        return json;
    }


    @Value("D:/Intellij/game/src/main/resources/static/postbar")
    private String uplpadPath;
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/setupPostbar")
    public String setupGame(@RequestParam("gid") String gid,
                            @RequestParam("uid") String uid,
                            @RequestParam("content") String content,
                            @RequestParam("points") String points,
                            @RequestParam(value = "picture", required = false) MultipartFile path_file[])
            throws IOException {
        List<postbar> all_entity = postbarRepository.findAll();
        int num = 0;
        for(int i = 0; i < all_entity.size(); i++){
            if(all_entity.get(i).getTid() > num){
                num = Math.toIntExact(all_entity.get(i).getTid());
            }
        }
        num++;
        String pathname = uplpadPath + "/" + num + "" + "postbar" + "";
        File file = new File(pathname);
        file.mkdirs();

        postbar p_bar = new postbar();
        p_bar.setTid(num);
        p_bar.setUid(Integer.parseInt(uid));
        p_bar.setGid(Integer.parseInt(gid));
        p_bar.setCreate_time(new Date());
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
        p_bar.setContent(last);
        p_bar.setPoints(Float.parseFloat(points));
        p_bar.setZan(0);
        p_bar.setCai(0);
        p_bar.setIsvideo(0);
        try {
            String fileName = path_file[0].getOriginalFilename();
            util.httpUpload(path_file, pathname, fileName);
            String path = "";
            path = "http://36058s3d36.zicp.vip/static/postbar/" + num + "postbar/" + fileName;
            p_bar.setPicture(path);
            if(path_file[0].getOriginalFilename().split("\\.")[1].equals("mp4")){
                p_bar.setIsvideo(1);
            }
        }catch (IndexOutOfBoundsException e){
            p_bar.setPicture(null);
        }
        postbarRepository.saveAndFlush(p_bar);
        return "1";
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/deleteByTid")
    public String deleteByTid(int id){
        postbarRepository.deleteByTid(id);
        return "delete success";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getPoints")
    public String getPoints(@RequestParam("gid") String gid){
        List<postbar> get_po = postbarRepository.findByGid(Integer.parseInt(gid));
        float points = 0;
        for(postbar p : get_po){
            points += p.getPoints();
        }
        if(get_po.size() == 0){
            points = 0;
        }else{
            points /= get_po.size();
        }
        return String.format("%.1f", points);
    }


}

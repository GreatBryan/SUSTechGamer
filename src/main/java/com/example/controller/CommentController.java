package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Util;
import com.example.dao.CommentDao;
import com.example.dao.PostbarRepository;
import com.example.dao.UserDao;
import com.example.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private Util util;
    @Autowired
    private CommentDao dao;
    @Autowired
    private PostbarRepository postbarRepository;
    @Autowired
    private UserDao userDao;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/addComment")
    public String addComment(@RequestParam("uid") String uid,
                             @RequestParam("tid") String tid,
                             @RequestParam("content") String content,
                             @RequestParam("isreid") String isreid,
                             @RequestParam("picture") MultipartFile path_file[]){
        int cid = dao.findAll().size();
        Comment toAdd = new Comment();
        toAdd.setCid(cid+1);
        toAdd.setUser(Integer.parseInt(uid));
        toAdd.setContent(content);
        toAdd.setTime(new Timestamp(System.currentTimeMillis()));
        toAdd.setDepth(dao.findAllByTiezi(Integer.parseInt(tid)).size()+1);
        toAdd.setDislike(1);
        toAdd.setLike_num(0);
        toAdd.setDislike(0);
        toAdd.setTiezi(Integer.parseInt(tid));
        toAdd.setIsvideo(0);
        toAdd.setReid(Integer.parseInt(isreid));
        toAdd.setParent(0);
        try {
            String path = "D:/Intellij/game/src/main/resources/static/comment/picture";
            File file = new File(path);
            file.mkdirs();
            int pid = 1;
            while (true) {
                String t_branch = path + "/" + pid + ".jpg";
                File f = new File(t_branch);
                if (!f.exists()) {
                    break;
                }
                pid++;
            }
            if(path_file[0].getOriginalFilename().split("\\.")[1].equals("jpg")){
                util.httpUpload(path_file, path, pid + ".jpg");
                toAdd.setParent(pid);
            }else if(path_file[0].getOriginalFilename().split("\\.")[1].equals("mp4")){
                util.httpUpload(path_file, path, pid + ".mp4");
                toAdd.setParent(pid);
                toAdd.setIsvideo(1);
            }
        }catch (IndexOutOfBoundsException e){

        }
        dao.saveAndFlush(toAdd);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/good")
    public String good(@RequestParam("cid") int cid){
        Comment get = dao.findByCid(cid).get(0);
        get.setLike_num(get.getLike_num() + 1);
        dao.saveAndFlush(get);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/bad")
    public String bad(@RequestParam("cid") int cid){
        Comment get = dao.findByCid(cid).get(0);
        get.setDislike(get.getDislike() + 1);
        dao.saveAndFlush(get);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllByTid")
    public JSONArray getAllByTid(@RequestParam("tid") int tid){
        List<Comment> ge = dao.findAllByTiezi(tid);
        JSONArray json = new JSONArray();
        for(Comment com : ge){
            JSONObject jo = new JSONObject();
            jo.put("cid", com.getCid());
            jo.put("uid", com.getUser());
            jo.put("name", userDao.findUserByUid(com.getUser()).get(0).getName());
            jo.put("re_name", com.getReid() == 0 ?
                    "" : userDao.findUserByUid(com.getReid()).get(0).getName());
            jo.put("gid", postbarRepository.findByTid(tid).get(0).getGid());
            jo.put("isvideo", com.getIsvideo());
            jo.put("reid", com.getReid());
            jo.put("points", postbarRepository.findByTid(tid).get(0).getPoints());
            jo.put("depth", com.getDepth());
            jo.put("path",
                    com.getParent() == 0 ? "" :
                            com.getIsvideo() == 0 ?
                                    "http://36058s3d36.zicp.vip/static/" +
                            "comment/picture/"+ com.getParent() +".jpg"
                                                    :
                                    "http://36058s3d36.zicp.vip/static/" +
                                            "comment/picture/"+ com.getParent() +".mp4");
            jo.put("release_time", com.getTime());
            jo.put("content", com.getContent());
            jo.put("display", com.getDislike());
            jo.put("good", com.getLike_num());
            jo.put("bad", com.getDislike());
            json.add(jo);
        }
        return json;
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("cid") int cid){
        dao.hideComment(cid);
        return "success";
    }



    @CrossOrigin
    @ResponseBody
    @GetMapping("/like")
    public String likeComment(@RequestParam("cid") String cid){
        Comment get_c = dao.findByCid(Integer.parseInt(cid)).get(0);
        get_c.setLike_num(get_c.getLike_num() + 1);
        dao.saveAndFlush(get_c);
        return "success";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/likeWithdraw")
    public String likeWithdraw(@RequestParam("cid") String cid){
        Comment get_c = dao.findByCid(Integer.parseInt(cid)).get(0);
        get_c.setLike_num(get_c.getLike_num() - 1);
        dao.saveAndFlush(get_c);
        return "success";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/dislike")
    public String dislike(@RequestParam("cid") String cid){
        Comment get_c = dao.findByCid(Integer.parseInt(cid)).get(0);
        get_c.setDislike(get_c.getDislike() + 1);
        dao.saveAndFlush(get_c);
        return "success";
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/dislikeWithdraw")
    public String dislikeWithdraw(@RequestParam("cid") String cid){
        Comment get_c = dao.findByCid(Integer.parseInt(cid)).get(0);
        get_c.setDislike(get_c.getDislike() - 1);
        dao.saveAndFlush(get_c);
        return "success";
    }



}

package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Util;
import com.example.dao.Dev_GameRepository;
import com.example.dao.GameRepository;
import com.example.dao.Label_GameRepository;
import com.example.domain.Game;
import com.example.domain.Label_Game;
import com.example.domain.dev_game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("game")
public class GameController{
    @Autowired
    private Label_GameRepository label_gameRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private Dev_GameRepository dev_gameRepository;
    @Autowired
    private Util util;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/adGetGame")
    public List<Game> adGetGame() {
        List<Game> ge = gameRepository.findAll();
        List<Game> re = new ArrayList<>();
        for(int i = ge.size() - 1; i >= 0; i--){
            re.add(ge.get(i));
        }
        return re;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllGame")
    public List<Game> findAll() {
        List<Game> get_game = gameRepository.findAll();
        for(Game g : get_game){
            String branch = "D:/Intellij/game/src/main/resources/static/game/" + g.getGid()
                    + "game/branch0.0/description.txt";
            File f = new File(branch);
            String content = "";
            if(f.exists()) {
                try {
                    String url = "";
                    FileReader in = new FileReader(f);
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                    while (true) {
                        url = br.readLine();
                        if (url != null) {
                            content += url;
                        } else break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            g.setDescription(content);
        }

        return gameRepository.findAll();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findByName")
    public List<Game> findByName(@RequestParam String name){
        List<Game> get_game = gameRepository.findAll();
        List<Game> re_1 = new ArrayList<>();
        for(Game g : get_game){
            if(g.getName().contains(name)){
                re_1.add(g);
            }
        }
        List<Label_Game> ge_lg = label_gameRepository.findAll();
        for(Label_Game l : ge_lg){
            if(l.getLabel().contains(name)){
                re_1.add(gameRepository.findById(l.getId()).get(0));
            }
        }
        return re_1;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findPriceByGid")
    public String findByName(@RequestParam int gid){
        return gameRepository.findById(gid).get(0).getPrice() + "";
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findById")
    public List<Game> findById(@RequestParam(value = "gid") long gid){
        return gameRepository.findById(gid);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAllBygid")
    public JSONObject getAllBygid(@RequestParam long gid){
        Game game = gameRepository.findById(gid).get(0);
        JSONObject jn = new JSONObject();
        jn.put("gid",gid);
        jn.put("name",game.getName());
        jn.put("release_date", game.getRelease_date().toString().split(" ")[0]);
        try {
            if (game.getEnd_time().after(new Date())) {
                jn.put("discount", String.format("%.2f", game.getDiscount()));
            } else {
                jn.put("discount", "1");
            }
        }catch (NullPointerException e){
            jn.put("discount", "1");
        }
        jn.put("dis_end_time", game.getEnd_time() == null ? "": game.getEnd_time().toString().split(" ")[0]);
        jn.put("description", game.getG_des() == null? "" : game.getG_des());
        jn.put("shoot_num",getShoot(gid+""));
        String[] tag = new String[5];
        List<Label_Game> de = label_gameRepository.findById((int)gid);
        for(int i = 0; i < 5; i++){
            tag[i] = i >= de.size() ? "" : de.get(i).getLabel();
        }
        jn.put("tag",tag);
        return jn;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findBranchBygid")
    public JSONArray findBranchBygid(@RequestParam long id){
        Game game = gameRepository.findById(id).get(0);
        String branch = "D:/Intellij/game/src/main/resources/static/game/" + id
                + "game/branch0.";
        JSONArray json = new JSONArray();
        int cnt = 0;
        while(true){
            String t_branch = branch + ("" + cnt) + "/description.txt";
            File f = new File(t_branch);
            if(!f.exists()){
                break;
            }
            String content = "";
            try {
                String url = "";
                FileReader in = new FileReader(f);
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                while(true){
                    url = br.readLine();
                    if(url != null){
                        content += url;
                    }else break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jo = new JSONObject();
            jo.put("description", content);
            jo.put("index", cnt);
            jo.put("gamepath", "http://36058s3d36.zicp.vip/static/game/" + id
                    + "game/branch0." + ("" + cnt) +"/" + gameRepository.findById(id).get(0).getPath());
            json.add(jo);
            cnt++;
        }
        return json;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getGidList")
    public String getGidList(@RequestParam("uid") String uid){
        List<Game> get_dev = gameRepository.findByUid(Integer.parseInt(uid));
        if(get_dev.size() == 0){
            return "0";
        }
        String re = "";
        int cnt = 0;
        for(Game d : get_dev){
            if(cnt == 0){
                re = re + d.getGid();
            }else{
                re = re + "," + d.getGid();
            }
            cnt++;
        }
        System.out.println(re);
        return re;
    }


    @Value("D:/Intellij/game/src/main/resources/static/game")
    private String uplpadPath;
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/setupGame")
    public String setupGame(
                            @RequestParam("uid") String uid,
                            @RequestParam("name") String name,
                            @RequestParam("Description") MultipartFile des_file[],
                            @RequestParam("price") double price,
                            @RequestParam("release") String release_date,
                            @RequestParam("path") MultipartFile path_file[])
            throws IOException {
        List<Game> all_game = gameRepository.findAll();
        long num = 0;
        for(int i = 0; i < all_game.size(); i++){
            if(all_game.get(i).getId() > num){
                num = all_game.get(i).getId();
            }
        }
        num++;
        String pathname = uplpadPath + "/" + num + "" + "game" + "/branch" + "0.0";
        File file = new File(pathname);
        file.mkdirs();
        String fileName = des_file[0].getOriginalFilename();
        //Add the path of description
        String Description = pathname + "/" + fileName + "";
        util.httpUpload(des_file,pathname, "description.txt");
        fileName = path_file[0].getOriginalFilename();
        //Add the path of the game entity
        String path = pathname + "/" + fileName + "";
        util.httpUpload(path_file, pathname, fileName);

        Game game = new Game();
        game.setUid(Integer.parseInt(uid));
        game.setId(num);
        game.setName(name);
        game.setDescription("http://36058s3d36.zicp.vip/static/game" + "/" + num + "" + "game");
        game.setPath(fileName);
        game.setPrice(price);
        game.setDiscount(1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            game.setRelease_date(df.parse(release_date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        gameRepository.saveAndFlush(game);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/newBranch")
    public String newbranch(
            @RequestParam("gid") String gid,
            @RequestParam("Description") MultipartFile des_file[],
            @RequestParam("path") MultipartFile path_file[]){
        Game game = gameRepository.findById(Integer.parseInt(gid)).get(0);
        String branch = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/branch0.";
        int cnt = 0;
        while(true){
            String t_branch = branch + ("" + cnt) + "/description.txt";
            File f = new File(t_branch);
            if(!f.exists()){
                break;
            }
            cnt++;
        }
        String pathname = uplpadPath + "/" + gid + "" + "game" + "/branch0." + cnt;
        File file = new File(pathname);
        file.mkdirs();
        String fileName = des_file[0].getOriginalFilename();
        //Add the path of description
        util.httpUpload(des_file,pathname, "description.txt");
        fileName = path_file[0].getOriginalFilename();
        //Add the path of the game entity
        util.httpUpload(path_file, pathname, fileName);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/newVideo")
    public String newVideo(
            @RequestParam("gid") String gid,
            @RequestParam("video") MultipartFile des_file[]){
        String video = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/video";
        File file = new File(video);
        file.mkdirs();
        util.httpUpload(des_file, video, "game.mp4");
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/newPicture")
    public String newPicture(
            @RequestParam("gid") String gid,
            @RequestParam("picture") MultipartFile des_file[]){
        String Picture = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/picture";
        File file = new File(Picture);
        file.mkdirs();
        util.httpUpload(des_file, Picture, "game.jpg");
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/newShoot")
    public String newShoot(
            @RequestParam("gid") String gid,
            @RequestParam("shoot") MultipartFile des_file[]){
        String shoot = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/shoot";
        File file = new File(shoot);
        file.mkdirs();
        int cnt = 1;
        while(true){
            String t_branch = shoot + "/" + cnt + "shoot.jpg";
            File f = new File(t_branch);
            if(!f.exists()){
                break;
            }
            cnt++;
        }
        String pathname = shoot + "/" + cnt + "shoot.jpg";
        //Add the path of description
        util.httpUpload(des_file,shoot, cnt + "shoot.jpg");
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getShoot")
    public int getShoot(@RequestParam("gid") String gid){
        String shoot = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/shoot";
        File file = new File(shoot);
        file.mkdirs();
        int cnt = 1;
        while(true){
            String t_branch = shoot + "/" + cnt + "shoot.jpg";
            System.out.println(t_branch);
            File f = new File(t_branch);
            if(!f.exists()){
                break;
            }
            cnt++;
        }
        return cnt-1;
    }




    @CrossOrigin
    @ResponseBody
    @RequestMapping("/deletebyID")
    public String deletebyID(long id){
        gameRepository.deleteById(id);
        return "delete success";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/update")
    public String update(@RequestParam("gid") String gid,
                         @RequestParam("name") String name,
                         @RequestParam("price") String price,
                         @RequestParam("release_date") String release_date,
                         @RequestParam("discount") String discount,
                         @RequestParam("end_time") String end_time,
                         @RequestParam("g_des") String g_des
                         ){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Game> seve_game = gameRepository.findById(Integer.parseInt(gid));
        for(int i = 0; i < seve_game.size(); i++){
            if(!name.equals("")){
                seve_game.get(i).setName(name);
            }
            if(!price.equals("")){
                seve_game.get(i).setPrice(Float.parseFloat(price));
            }
            if(!release_date.equals("")){
                try {
                    seve_game.get(i).setRelease_date(sdf.parse(release_date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(!discount.equals("")){
                seve_game.get(i).setDiscount(Float.parseFloat("0."+discount));
            }
            if(!end_time.equals("")){
                try {
                    seve_game.get(i).setEnd_time(sdf.parse(end_time));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(!g_des.equals("")){

                String[] temp = g_des.split("%20");
                String last = "";
                for(String str:temp){
                    last = last +  str + " ";
                }
                temp = last.split("%0A");
                last = "";
                for(String str:temp){
                    last = last +  str + "\n";
                }

                seve_game.get(i).setG_des(last);
            }
            gameRepository.saveAndFlush(seve_game.get(i));
        }
        return "1";
    }




}


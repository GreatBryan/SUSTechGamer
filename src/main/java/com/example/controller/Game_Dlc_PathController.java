package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.Util;

import com.example.dao.GameRepository;
import com.example.dao.Game_Dlc_PathRepository;
import com.example.dao.ugdDao;
import com.example.domain.Game;
import com.example.domain.User;
import com.example.domain.User_game_dlc;
import com.example.domain.game_dlc_path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("game_dlc_path")
public class Game_Dlc_PathController {

    @Autowired
    private Game_Dlc_PathRepository game_dlc_pathRepository;
    @Autowired
    private ugdDao ugdDao;
    @Autowired
    private Util util;
    @Autowired
    private GameRepository gameRepository;

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getAll")
    public List<game_dlc_path> findAll() {
        return game_dlc_pathRepository.findAll();
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findBygid")
    public List<game_dlc_path> findBygid(int gid) {
        return game_dlc_pathRepository.findByGid(gid);
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findPriceByGidAndDLC")
    public String findByName(@RequestParam int gid,
                             @RequestParam int dlc_id){
        return game_dlc_pathRepository.findByGidAndDlcid(gid,dlc_id).get(0).getPrice()+"";
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping("/setupGdp")
    public String setupGame(@RequestParam("gid") int gid,
                            @RequestParam("did") int did,
                            @RequestParam("Description") MultipartFile des_file[],
                            @RequestParam("path") MultipartFile path_file[],
                            @RequestParam("price") double price)
            throws IOException {
        List<game_dlc_path> all_entity = game_dlc_pathRepository.findAll();
        int num = 0;
        for(int i = 0; i < all_entity.size(); i++){
            if(all_entity.get(i).getKeyid() > num){
                num = all_entity.get(i).getKeyid();
            }
        }
        num++;
        String branch = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/dlc";
        int cnt = 1;
        while(true){
            String t_branch = branch + ("" + cnt) + "/description.txt";
            File f = new File(t_branch);
            if(!f.exists()){
                break;
            }
            cnt++;
        }
        String pathname = "D:/Intellij/game/src/main/resources/static/game/" + gid
                + "game/dlc" + cnt;
        File file = new File(pathname);
        file.mkdirs();
        String fileName = des_file[0].getOriginalFilename();
        //Add the path of description
        util.httpUpload(des_file,pathname, "description.txt");
        fileName = path_file[0].getOriginalFilename();
        //Add the path of the game entity
        util.httpUpload(path_file, pathname, fileName);
        game_dlc_path gdp = new game_dlc_path();
        gdp.setGid(gid);
        gdp.setDid(did);
        gdp.setPath("dlc"+ cnt + "/" + fileName);
        gdp.setKeyid(num);
        gdp.setPrice(price);
        gdp.setDlcid(cnt);
        game_dlc_pathRepository.saveAndFlush(gdp);
        return "1";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/findDLCBygid")
    public JSONArray findDLCBygid(@RequestParam long gid){
        List<game_dlc_path> gdp = game_dlc_pathRepository.findByGid((int)gid);
        JSONArray json = new JSONArray();
        for(game_dlc_path g : gdp){
            String content = "";
            String t_branch = "D:/Intellij/game/src/main/resources/static/game/" + gid
                    + "game/" + g.getPath().split("/")[0] + "/description.txt";
            File f = new File(t_branch);
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
            jo.put("price", g.getPrice());
            jo.put("index", g.getDlcid());
            jo.put("path", "http://36058s3d36.zicp.vip/static/game/" + gid+ "game/" + g.getPath());

            json.add(jo);
        }
        return json;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/getDlcByUid")
    public JSONArray getDlcByUid(@RequestParam("uid") int uid){
        List<User_game_dlc> ge = ugdDao.findByUid(uid);
        JSONArray re = new JSONArray();
        for(User_game_dlc ugd : ge){
            JSONObject json = new JSONObject();
            json.put("gid", ugd.getGid());
            json.put("name", gameRepository.findById(ugd.getGid()).get(0).getName());
            json.put("index", ugd.getDlc());

            String content = "";
            String t_branch = "D:/Intellij/game/src/main/resources/static/game/" + ugd.getGid()
                    + "game/" +
                    game_dlc_pathRepository.findByGidAndDlcid(ugd.getGid(), ugd.getDlc()).get(0)
                    .getPath().split("/")[0] + "/description.txt";
            File f = new File(t_branch);
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

            json.put("description", content);

            re.add(json);
        }
        return re;
    }


    @CrossOrigin
    @Transactional
    @ResponseBody
    @RequestMapping("/deleteByKeyid")
    public String deleteByKeyid(int id){
        game_dlc_pathRepository.deleteByKeyid(id);
        return "delete success";
    }


}

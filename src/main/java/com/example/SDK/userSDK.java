package com.example.SDK;

import com.example.Util;
import com.example.dao.GameRepository;
import com.example.dao.Game_Dlc_PathRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class userSDK {
    @Autowired
    private Util util;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private Game_Dlc_PathRepository game_dlc_pathRepository;

    @RequestMapping("/downloadDlc")
    public String downloadDlc(HttpServletResponse response,
                              @RequestParam("gid") int gid,
                              @RequestParam("dlcid") int dlcid){
        System.out.println(game_dlc_pathRepository.findByGidAndDlcid(gid, dlcid).size());
        String dlc_path = "D:/Intellij/game/src/main/resources/static/game/"+gid+"game/"+
                game_dlc_pathRepository.findByGidAndDlcid(gid, dlcid).get(0).getPath();
        util.download(response, dlc_path,
                game_dlc_pathRepository.findByGidAndDlcid(gid, dlcid).get(0).getPath().split("/")[1]);
        return "success";
    }

    @RequestMapping("/download")
    public String userdownload(HttpServletResponse response, @RequestParam("uid") String uid, @RequestParam("gid") String gid) throws FileNotFoundException {
        try {
            //写入的txt文档的路径
            PrintWriter pw = new PrintWriter("info.txt");
            //写入的内容
            pw.write("" + uid  + "\n" + gid);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path = "D:/Intellij/game/src/main/resources/static/game/" + gid + "game"
                + "/branch0.0/" + gameRepository.findById(Integer.parseInt(gid)).get(0).getPath();
        System.out.println(path);
        File[] srcFiles = { new File(path), new File("info.txt")};
        File zipFile = new File("ZipFile.zip");
        // 调用压缩方法
        util.zipFiles(srcFiles, zipFile);
        util.download(response, "ZipFile.zip", "ZipFile.zip");
        return "success";
    }

    @RequestMapping("/getSave")
    public String getSave(@RequestParam("uid") String uid,
                           @RequestParam("gid") String gid){
        String path = "D:/Intellij/game/src/main/resources/static/game/" + gid +"game";
        path = path + "/"+uid + "user";
        int cnt = 1;
        String re_str = "";
        while(true){
            File file = new File(path + "/" + cnt + ".txt");
            if(file.exists()){
                try {
                    String url = "";
                    FileReader in = new FileReader(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    while(true){
                        url = br.readLine();
                        if(url != null){
                            re_str = re_str + url;
                        }else break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                re_str += "|";
            }else{
                break;
            }
            cnt++;
        }
        return re_str;
    }

    @RequestMapping("/uploadSave")
    public String saveFile(@RequestParam("uid") String uid,
                           @RequestParam("gid") String gid,
                           @RequestParam("info") String info){
        String path = "D:/Intellij/game/src/main/resources/static/game/" + gid +"game";
        path = path + "/"+uid + "user";
        File file = new File(path);
        file.mkdirs();
        int count = 1;
        while (true) {
            File f = new File(path + "/" + count + ".txt");
            if (!f.exists()) {
                break;
            }
            count++;
            if(count > 5){
                count = 1;
                break;
            }
        }
        File f = new File(path + "/" + count + ".txt");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //写入的txt文档的路径
            PrintWriter pw = new PrintWriter(path + "/" + count + ".txt");
            //写入的内容
            pw.write(info);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);

        return "success";
    }

    @RequestMapping("/uploadRank")
    public String uploadRank(@RequestParam("gid") String gid,
                           @RequestParam("info") String info){
        String path = "D:/Intellij/game/src/main/resources/static/game/" + gid +"game/rank.txt";
        //Create new user file
        File f = new File(path);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String rank = "";
        try {
            String url = "";
            FileReader in = new FileReader(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            while(true){
                url = br.readLine();
                if(url != null){
                    rank = rank + url + ";";
                }else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] get_rank = rank.split(";");

        FileWriter fw = null;
        boolean isInfo = false;
        try {
            fw = new FileWriter(f.getAbsoluteFile(), false);
            BufferedWriter bw = new BufferedWriter(fw);
            int cnt = 0;
            for(String str : get_rank){
                if (str == "") break;
                if(cnt >= 10){
                    break;
                }
//                System.out.println("Str:" + str);
                if(Integer.parseInt(str.split(",")[1]) <= Integer.parseInt(info.split(",")[1])
                && !isInfo){
                    isInfo = true;
                    bw.write(info);
                    bw.newLine();
                }
                bw.write(str);
                bw.newLine();
                cnt++;
            }
            if(!isInfo){
                isInfo = true;
                bw.write(info);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    @RequestMapping("/getRank")
    public String getRank(@RequestParam("gid") String gid){
        String path = "D:/Intellij/game/src/main/resources/static/game/" + gid +"game/rank.txt";
        //Create new user file
        File f = new File(path);
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String re_str = "";
        try {
            String url = "";
            FileReader in = new FileReader(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            while(true){
                url = br.readLine();
                if(url != null){
                    re_str = re_str + url + ";";
                }else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re_str;
    }


}

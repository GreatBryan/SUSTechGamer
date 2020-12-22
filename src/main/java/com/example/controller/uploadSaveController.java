package com.example.controller;

import com.example.Util;
import com.example.dao.GameRepository;
import com.example.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("upload")
public class uploadSaveController {
    @Autowired
    private Util util;

    @Value("C:/Users/13681/Desktop")
    private String uplpadPath;
    @ResponseBody
    @RequestMapping("/setupGame")
    public String setupGame(@RequestParam("uid") int uid,
                            @RequestParam("gid") int gid,
                            @RequestParam("Description") MultipartFile des_file[])
            throws IOException {
        String pathname = uplpadPath + "/" + gid + "" + "game" + "/user" + uid;
        File file = new File(pathname);
        file.mkdirs();
        String fileName = des_file[0].getOriginalFilename();
        //Add the path of description
        String Description = pathname + "/" + fileName + "";
        util.httpUpload(des_file,pathname, fileName);

        return "success";
    }
}

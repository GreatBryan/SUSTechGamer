package com.example.controller;


import com.example.Util;
import com.example.dao.Label_GameRepository;
import com.example.domain.Game;
import com.example.domain.Label_Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("label_game")
public class Label_GameController {
    @Autowired
    private Label_GameRepository label_gameRepository;
    @Autowired
    private Util util;

    @ResponseBody
    @RequestMapping("/getAll")
    public List<Label_Game> findAll() {
        return label_gameRepository.findAll();
    }

    @ResponseBody
    @RequestMapping("/findById")
    public List<Label_Game> findById(@RequestParam int id){
        return label_gameRepository.findById(id);
    }

    @ResponseBody
    @RequestMapping("/findByKeyid")
    public List<Label_Game> findByKeyid(@RequestParam int id){
        return label_gameRepository.findByKeyid(id);
    }

    @ResponseBody
    @RequestMapping("/findByLabel")
    public List<Label_Game> findByLabel(@RequestParam String label){
        return label_gameRepository.findByLabel(label);
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/deleteByKeyid")
    public String deleteByKeyid(int id){
        label_gameRepository.deleteByKeyid(id);
        return "delete success";
    }

    @CrossOrigin
    @RequestMapping("/upLabel")
    public String findByLabel(@RequestParam(name = "label") String label,
                              @RequestParam(name = "gid") String gid){
        List<Label_Game> all_game = label_gameRepository.findAll();
        int num = 0;
        for(int i = 0; i < all_game.size(); i++){
            if(all_game.get(i).getId() > num){
                num = all_game.get(i).getId();
            }
        }
        num++;
        Label_Game lg = new Label_Game();
        lg.setId(num);
        lg.setKeyid(Integer.parseInt(gid));
        lg.setLabel(label);
        label_gameRepository.saveAndFlush(lg);
        return "1";
    }


}

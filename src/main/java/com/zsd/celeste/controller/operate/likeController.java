package com.zsd.celeste.controller.operate;


import com.zsd.celeste.enums.LikeType;
import com.zsd.celeste.service.operate.LikeService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 通用点赞接口
 * */
@RestController
@RequestMapping("/like")
public class likeController {

    @Autowired
    private LikeService service;



    /**
     * 点赞 、 取消点赞
     * */
    @PostMapping("/{type}/{id}")
    Result like(@PathVariable Integer id,@PathVariable String type){
        return like(id,type,true);
    }
    @DeleteMapping("/{type}/{id}")
    Result unlike(@PathVariable Integer id,@PathVariable String type){
        return like(id,type,false);
    }




    /**
     * 点赞 、 取消点赞
     * */
    Result like(Integer id, String type, boolean b){
        // 获取点赞类型
        LikeType t = LikeType.getType(type);
        if(t == null)
            throw new RuntimeException("评论类型错误");
        // todo
        return null;
    }

}

package com.zsd.celeste.controller.operate;


import com.zsd.celeste.service.operate.UserFollowService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * todo 关注接口
 *
 * */
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private UserFollowService service;


    /**
     * 获取关注列表
     * */
    @GetMapping("/{uid}/{index}")
    Result getUserFollow(@PathVariable Integer uid, @PathVariable Integer index) {
        return Result.ok(service.getFollow(uid,index));
    }

    /**
     * 获取被关注的丝列表
     * */
    @GetMapping("/by/{uid}/{index}")
    Result getUserFollowed(@PathVariable Integer uid, @PathVariable Integer index) {
        return Result.ok(service.getFollowed(uid,index));
    }




    /**
     * 关注、取消关注
     * */
    @PostMapping("/{uid}")
    Result follow(@PathVariable Integer uid) {
        return follow(uid,true);
    }
    @DeleteMapping("/{uid}")
    Result unfollow(@PathVariable Integer uid) {
        return follow(uid,false);
    }

    Result follow(@PathVariable Integer uid,boolean b) {
        // todo get uid
        return Result.judge(service.follow(null,uid,b));
    }
}

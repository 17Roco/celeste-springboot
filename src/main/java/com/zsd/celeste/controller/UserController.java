package com.zsd.celeste.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * (User)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:43:08
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService service;

    /**
     * 查询
     * @return 查询结果
     */
    @GetMapping("/list")
    Result getPage(){
        return getPage(1);
    }
    @GetMapping("/list/{index}")
    Result getPage(@PathVariable Integer index){
        return Result.page(service.page(index));
    }

    /**
     * 根据id查询GET
     * @param id 查询id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    Result getById(@PathVariable Integer id){
        return Result.success(service.getById(id));
    }


    /**
     * 增加记录POST - 注册
     * @param user 实体类对象
     * @return 结果
     */
    @PostMapping()
    Result signUp(@RequestBody User user){
        user.setUid(null);
        boolean save = service.save(user);
        return Result.judge(save);
    }

    /**
     * 更新
     * @param user 更新数据
     * @return 结果
     */
    @PutMapping()
    Result update(@RequestBody User user){
        boolean b = service.updateById(user);
        return Result.judge(b);
    }

    /**
     * 根据id删除
     * @param id 删除id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    Result delete(@PathVariable Integer id){
        boolean b = service.removeById(id);
        return Result.judge(b);
    }

    /**
     * 根据id获取文章
     * @param id index
     * @return 结果
     * */
    @GetMapping("/article/{id}")
    Result getArticle(@PathVariable Integer id){
        return getArticle(id, 1);
    }
    @GetMapping("/article/{id}/{index}")
    Result getArticle(@PathVariable Integer id,@PathVariable Integer index){
        return Result.success(service.getArticleByUser(id,index));
    }
}


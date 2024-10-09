package com.zsd.celeste.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsd.celeste.entity.ArticleFilter;
import com.zsd.celeste.entity.DO.ArticleUpdate;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.base.controller.BaseListPageController;
import com.zsd.celeste.util.base.controller.rest.BaseDeleteByIdController;
import com.zsd.celeste.util.base.controller.rest.BaseGetByIdController;
import com.zsd.celeste.util.base.service.BaseService;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

/**
 * (Article)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@RestController
@RequestMapping("/article")
public class ArticleController implements BaseGetByIdController<Article> {
    
    @Autowired
    private ArticleService service;
    @Autowired
    private TagService tagService;
    public BaseService<Article> getService() {
        return service;
    }


    @GetMapping("/filter")
    Result filter(@RequestParam(required = false) Integer index, @RequestParam(required = false) String order, @RequestParam(required = false) String tag, @RequestParam(required = false) Date beginTime, @RequestParam(required = false) Date endTime, @RequestParam(required = false) Integer uid){
        ArticleFilter filter = new ArticleFilter(index,order,tag,beginTime,endTime,uid);
        return DataResult.ok(service.page(filter.getIndex(),filter.wrapper(tagService)));
    }
    @GetMapping({"/self","/self/{index}"})
    Result getByUser(@PathVariable(required = false) Integer index){
        return DataResult.ok(
                service.page(
                    index==null?1:index,
                    new QueryWrapper<Article>().eq("uid",AutUtil.self().getUid())
                )
        );
    }

    /**
     * 保存 、 更新
     * */
    @PostMapping("/")
    Result save(@RequestBody ArticleUpdate update) {
        Integer aid = service.saveWithTags(update);
        return StreamResult.create(aid != null).put("aid",aid);
    }
    @PutMapping("/{aid}")
    Result update(@RequestBody ArticleUpdate update,@PathVariable Integer aid) {
        return Result.judge(service.update(update,aid));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@articleServiceImpl.needBySelf(#id) != null ")
    public Result delete(@PathVariable Integer id) {
        return Result.judge(service.removeById(id));
    }

    /**
     * 点赞 、 取消点赞
     * */
    Result like(Integer aid, boolean b){
        return Result.judge(service.like(aid, AutUtil.self().getUid(), b));
    }
    @PostMapping("/like/{aid}")
    Result like(@PathVariable Integer aid){
        return like(aid,true);
    }
    @PostMapping("/unlike/{aid}")
    Result unlike(@PathVariable Integer aid){
        return like(aid,false);
    }







    /**
     * 添加 、 删除 文章标签
     * */
    Result updateTag(Integer aid, String tag,boolean b){
        return Result.judge(b ? service.addTag(aid,tag) : service.delTag(aid,tag));
    }
    @PreAuthorize("@articleServiceImpl.needBySelf(#aid) != null ")
    @PostMapping("/add_tag/{aid}/{tag}")
    Result addTag(@PathVariable Integer aid, @PathVariable String tag){
        return updateTag(aid,tag,true);
    }
    @PreAuthorize("@articleServiceImpl.needBySelf(#aid) != null ")
    @PostMapping("/del_tag/{aid}/{tag}")
    Result delTag(@PathVariable Integer aid, @PathVariable String tag){
        return updateTag(aid,tag,false);
    }
}


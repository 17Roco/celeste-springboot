package com.zsd.celeste.controller.data;

import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.data.TagService;
import com.zsd.celeste.util.result.Result;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tag")
public class TagController {
    
    @Autowired
    private TagService service;

    /**
     * 获取标签列表
     * */
    @GetMapping("/list")
    Result getList() {
        return Result.ok(service.list());
    }

    /**
     * 添加标签
     * */
//    @PreAuthorize("@autUtil.isAdmin()") todo admin
    @PostMapping("/create")
    public Result add(@RequestBody TagForm form) {
        Tag tag = new Tag(null, form.getTitle(), form.getInfo(), 0);
        return Result.judge(service.save(tag));
    }




    /**
     * 添加 、 删除 文章标签
     * */
    Result updateTag(Integer aid, String tag,boolean b){
//        return Result.judge(b ? service.addTag(aid,tag) : service.delTag(aid,tag));
        return null;
    }
    @PostMapping("/add/{aid}")
    Result addTag(@PathVariable Integer aid, @RequestParam String tag){
        return updateTag(aid,tag,true);
    }
    @PostMapping("/del/{aid}")
    Result delTag(@PathVariable Integer aid, @RequestParam String tag){
        return updateTag(aid,tag,false);
    }

}

@Data
class TagForm {
    private String title;
    private String info;
}

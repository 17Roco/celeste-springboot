package com.zsd.celeste.entity.VO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Integer cid;
    private Integer pcid;
    private String text;
    private Date time;
    private Integer like;

    private UserInfoVo user;
    private IPage<CommentVo> comments;
}

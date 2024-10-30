package com.zsd.celeste.entity.DO;

import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.util.AutUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdate {
    private String title;
    private String context;
}

package com.zsd.celeste.entity.DO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdate {
    private Integer aid;
    private String title;
    private String context;
    private Map<String, Boolean> tagUpdate;
}

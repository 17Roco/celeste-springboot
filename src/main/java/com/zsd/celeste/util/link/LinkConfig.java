package com.zsd.celeste.util.link;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkConfig {
    private String tableName;
    private String a;
    private String b;

    LinkConfig toRe(){
        return new LinkConfig(tableName,b,a);
    }
}

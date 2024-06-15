package com.zsd.celeste.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkConfig {
    private String tableName;
    private String idField;
    private String linkField;
}

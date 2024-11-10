package com.zsd.celeste.service;

import com.zsd.celeste.enums.ResourceNameSpace;
import org.springframework.web.multipart.MultipartFile;

public interface FileResourceService {

    /*
    * 文件的保存路径
    * */
    String getSavePath();

    String saveResource(MultipartFile data,ResourceNameSpace nameSpace);
}

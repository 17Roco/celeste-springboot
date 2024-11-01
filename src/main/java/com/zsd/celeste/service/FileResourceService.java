package com.zsd.celeste.service;

import com.zsd.celeste.enums.ResourceNameSpace;
import org.springframework.web.multipart.MultipartFile;

public interface FileResourceService {

    String getBasePath();

    String saveImg(MultipartFile file, ResourceNameSpace nameSpace);
}

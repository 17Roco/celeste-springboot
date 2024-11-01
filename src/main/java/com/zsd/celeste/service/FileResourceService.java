package com.zsd.celeste.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileResourceService {

    String getBasePath();

    String saveImg(MultipartFile file);
}

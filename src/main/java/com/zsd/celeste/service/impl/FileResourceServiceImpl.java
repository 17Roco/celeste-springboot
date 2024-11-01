package com.zsd.celeste.service.impl;

import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.util.HashUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileResourceServiceImpl implements FileResourceService {

    @Autowired
    private HashUtil hashUtil;
    @Getter
    private final String basePath = "D:\\project\\jar\\static\\";
    private String getPath(ResourceNameSpace resourceNameSpace) {
        return basePath + resourceNameSpace.getPath();
    }




    private String saveResource(MultipartFile file) {
        if (file.isEmpty())
            throw new RuntimeException("文件为空");
        String filename = hashUtil.hash(file.getOriginalFilename());
        File uploadedFile = new File(basePath + filename);
        try {
            // todo 判断文件类型，判断文件是否重名
            file.transferTo(uploadedFile);
        } catch (Exception e) {
            throw new RuntimeException("保存失败");
        }
        return filename;
    }

    @Override
    public String saveImg(MultipartFile file) {
        return saveResource(file);
    }


}

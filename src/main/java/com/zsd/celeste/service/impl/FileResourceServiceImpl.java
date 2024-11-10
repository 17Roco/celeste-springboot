package com.zsd.celeste.service.impl;

import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.exception.exception.ResourceSaveFailEx;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.util.HashUtil;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Service
@Slf4j
public class FileResourceServiceImpl implements FileResourceService {

    @Autowired
    private HashUtil hashUtil;

    @Value("${celeste.save-path}")
    private String savePath;

    @PostConstruct
    public void init() {
        log.info("savePath: {}", savePath);
    }

    /**
     * 获取保存路径
     * */
    public String getSavePath() {
        // 获取文件夹路径
        File file = new File(savePath + "static");
        // 不存在或不是文件夹，则创建
        if (!file.exists() || !file.isDirectory()){
            try {
                Files.createDirectories(file.toPath());
            } catch (IOException e) {
                throw new ResourceSaveFailEx("路径创建失败");
            }
        }
        return savePath;
    }

    /**
     * 获取保存名称
     * */
    private String getSaveName(ResourceNameSpace nameSpace,String originalName){
        return "static/" + nameSpace.getPath() + "_" + hashUtil.hash(originalName);
    }


    /**
     * 保存到硬盘
     * */
    private void saveFile(String img,MultipartFile data){
        try {
            File file = new File(getSavePath() + img);
            data.transferTo(file);
        } catch (IOException e) {
            throw new ResourceSaveFailEx(e.getMessage());
        }
    }



    /**
     * 保存资源
     * */
    public String saveResource(MultipartFile data,ResourceNameSpace nameSpace) {
        if (Objects.isNull(data) ||data.isEmpty())
            throw new ResourceSaveFailEx("文件为空");
        // 设定图片路径
        String img = getSaveName(nameSpace,data.getOriginalFilename());
        // 保存图片
        saveFile(img,data);
        // 返回图片路径
        return "/" + img;
    }

}

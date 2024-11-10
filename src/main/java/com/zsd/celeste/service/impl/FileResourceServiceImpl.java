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
    @Getter
    @Value("${celeste.save-path}")
    private String savePath;

    @PostConstruct
    public void init() {
        log.info("savePath: {}", savePath);
    }

    /**
     * 获取保存的路径，文件夹不存在则创建
     * */
    private String getPath(ResourceNameSpace resourceNameSpace) {
        // 获取文件夹路径
        File file = new File(getSavePath() + resourceNameSpace.getPath());
        // 不存在或不是文件夹，则创建
        if (!file.exists() || !file.isDirectory()){
            try {
                Files.createDirectories(file.toPath());
            } catch (IOException e) {
                throw new ResourceSaveFailEx("路径创建失败");
            }
        }
        // 返回路径
        return file.getPath();
    }

    /**
     * 获取保存的文件信息
     * */
    private File getSaveInfo(ResourceNameSpace nameSpace,String filename){
        // 获取路径
        String path = getPath(nameSpace);
        // 生存hash文件名
        String hashName = hashUtil.hash(filename);
        // 创建文件
        File saveFile = new File(path + "/" + hashName);
        if (saveFile.exists())
            throw new ResourceSaveFailEx("文件已存在");
        return saveFile;
    }

    /**
     * 保存到硬盘
     * */
    private void saveFile(File file,MultipartFile data){
        try {
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
        File file = getSaveInfo(nameSpace,data.getOriginalFilename());
        // 保存图片
        saveFile(file,data);
        // 返回图片路径
        return file.getPath();
    }

}

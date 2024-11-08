package com.zsd.celeste.service.impl;

import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.exception.exception.ResourceSaveFailEx;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.util.HashUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileResourceServiceImpl implements FileResourceService {

    @Autowired
    private HashUtil hashUtil;
    @Getter
    private final String basePath = "D:\\project\\jar\\static\\";

    /**
     * 获取保存的路径，文件夹不存在则创建
     * */
    private String getPath(ResourceNameSpace resourceNameSpace) {
        File file = new File(getBasePath() + resourceNameSpace.getPath()+"\\");
        // 不存在则创建
        if (!file.exists() || file.isFile()){
            if (!file.mkdir())
                throw new ResourceSaveFailEx("路径创建失败");
        }
        // 返回路径
        return file.getPath();
    }

    /**
     * 获取保存的文件信息
     * */
    private File getSaveInfo(ResourceNameSpace nameSpace,String filename){
        String saveFilename = hashUtil.hash(filename);
        File saveFile = new File(getPath(nameSpace) + "\\" + saveFilename);
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

    private String saveResource(MultipartFile file,ResourceNameSpace nameSpace) {
        if (file.isEmpty())
            throw new ResourceSaveFailEx("文件为空");
        // 设定图片路径
        File uploadedFile = getSaveInfo(nameSpace,file.getOriginalFilename());
        // 保存图片
        saveFile(uploadedFile,file);
        return nameSpace.getPath() + "/" + uploadedFile.getName();
    }



    /**
     * 保存图片
     * */
    public String saveImg(MultipartFile file,ResourceNameSpace nameSpace) {
        return saveResource(file,nameSpace);
    }


}

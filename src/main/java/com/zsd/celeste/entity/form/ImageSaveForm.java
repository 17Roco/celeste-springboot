package com.zsd.celeste.entity.form;

import com.zsd.celeste.entity.PO.UserPojo;
import io.lettuce.core.output.KeyValueStreamingChannel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageSaveForm<T extends UserPojo> {
    private MultipartFile file;
    private KeyValueStreamingChannel<T, String> field;
    private String namespace;
}

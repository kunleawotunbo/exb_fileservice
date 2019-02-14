/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.payload;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Olakunle Awotunbo
 */
@Data
public class FileBucket {
    private Integer id;   
    private boolean enabled;
    private MultipartFile[] files;
    private MultipartFile file;
    private int code;
    private String description;
    private String keyReferenceNo;
}

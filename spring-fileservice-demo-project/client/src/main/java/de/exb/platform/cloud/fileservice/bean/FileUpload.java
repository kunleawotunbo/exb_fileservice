/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.bean;

import java.time.LocalDateTime;

/**
 *
 * @author Olakunle Awotunbo
 */
public class FileUpload {
    
    private Long id;
    private String originalFileName;
    private String batchUploadFileName;
    
    private String referenceNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Olakunle Awotunbo
 */
@Entity
@Table(name = "file_uploads")
@Data 
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    private String originalFileName;
    
    @NotBlank
    @Size(max = 200)
    private String batchUploadFileName;
    
    @NotBlank
    @Size(max = 20)
    private String referenceNumber;
    
    @NotNull
    private LocalDateTime createdAt;
    
    @NotNull
    private LocalDateTime updatedAt;

    public FileUpload(Long id, String originalFileName, String batchUploadFileName, String referenceNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.batchUploadFileName = batchUploadFileName;
        this.referenceNumber = referenceNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public FileUpload() {
    }
    
    
    
}

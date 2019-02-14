/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.model;

import de.exb.platform.cloud.fileservice.model.audit.DateAudit;
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
    
}

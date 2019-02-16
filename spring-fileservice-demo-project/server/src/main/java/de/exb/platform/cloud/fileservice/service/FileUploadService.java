/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.service;

import de.exb.platform.cloud.fileservice.model.FileUpload;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface FileUploadService {
    Optional<FileUpload> findByReferenceNumber(String referenceNumber);
    Optional<FileUpload> findByOriginalFileName(String originalFileName);
    Optional<FileUpload> findById(Long id);
    FileUpload save(FileUpload fileUpload);
    List<FileUpload> findAll();
    Page<FileUpload> findAll(Pageable pageable);
    public void delete(FileUpload fileUpload);  
}

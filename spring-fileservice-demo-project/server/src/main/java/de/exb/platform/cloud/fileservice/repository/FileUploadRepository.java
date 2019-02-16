/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.repository;

import de.exb.platform.cloud.fileservice.model.FileUpload;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  FileUploadRepository to access the database
 * @author Olakunle Awotunbo
 */
@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long>{     
    Optional<FileUpload> findByReferenceNumber(String referenceNumber);
    Optional<FileUpload> findByOriginalFileName(String originalFileName);
    Optional<FileUpload> findById(Long id);
    FileUpload save(FileUpload fileUpload);
}

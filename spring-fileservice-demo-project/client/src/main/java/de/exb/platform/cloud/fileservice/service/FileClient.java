/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.service;

import de.exb.platform.cloud.fileservice.bean.FileUpload;
import de.exb.platform.cloud.fileservice.bean.RequestBean;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Olakunle Awotunbo
 */
public interface FileClient {

    List<FileUpload> getAllFileUpload();

    FileUpload getById(Long id);

    HttpStatus addFile(RequestBean requestBean);

    void deleteFileUpload(String fileName);
    
    ResponseEntity getByFileName(String fileName);
}

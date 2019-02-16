/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.service;

import de.exb.platform.cloud.fileservice.model.FileUpload;
import de.exb.platform.cloud.fileservice.payload.FileBucket;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service
@Slf4j
public class HelperService {
    
    @Value("${app.uploadPath}")
    private String uploadPath;
    
    @Autowired
    private FileUploadService fileUploadService;
    
    
    @Transactional
    public void deleteFile(String aPath, FileUpload fileUpload){
        fileUploadService.delete(fileUpload);
        try {
            
            Path filePath = Paths.get(aPath);
            Files.delete(filePath);
        } catch (IOException ex) {
            log.error("Error - " + ex.getMessage());
        } 
        
    }
     
    public LocalDateTime getCurrentDateTime() {
        
        return LocalDateTime.now();
    }
    public Map fileUpload(MultipartFile dFile) throws FileServiceException {

        
        MultipartFile file = dFile;
        Map<String, String> map = new HashMap();

        if (!file.isEmpty()) {
            try {
                String originalFilename = file.getOriginalFilename();
                String rootPath = uploadPath;
                String referenceNumber = String.valueOf(System.currentTimeMillis());
                File dir = new File(rootPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());
                String serverFileName = rootPath + File.separator + baseName + "_" + referenceNumber + "." + ext;
                String batchUploadFileName = baseName + "_" + referenceNumber + "." + ext;

                File destinationFile = new File(serverFileName.trim());
                file.transferTo(destinationFile);
                log.info(" Uploaded successful");

                map.put("originalFilename", originalFilename);
                map.put("referenceNumber", referenceNumber);
                map.put("serverFileName", serverFileName);
                map.put("batchUploadFileName", batchUploadFileName);

            } catch (Exception e) {
                e.printStackTrace(); 
                log.error(" Upload failed " + e.getMessage());
                throw new FileServiceException("cannot open entry", e);
                
            }
        } else {
            log.info("File is empty");
        }

        return map;
    }
}

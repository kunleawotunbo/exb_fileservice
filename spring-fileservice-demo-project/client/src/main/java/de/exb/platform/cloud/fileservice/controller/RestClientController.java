/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.controller;

import de.exb.platform.cloud.fileservice.bean.FileUpload;
import de.exb.platform.cloud.fileservice.service.FileClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Olakunle Awotunbo
 */
@RestController
@RequestMapping("/api")
public class RestClientController {

    @Autowired
    FileClient fileClient;
    
    @GetMapping("/all")
    public List<FileUpload> getAllFiles() {

        return fileClient.getAllFileUpload();
    }
}

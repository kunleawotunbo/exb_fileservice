/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.service;

import de.exb.platform.cloud.fileservice.bean.FileUpload;
import de.exb.platform.cloud.fileservice.bean.RequestBean;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service
public class FileClientImpl implements FileClient {

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.hostURL}")
    private String hostURL;

    @Value("${app.aSessionId}")
    private String aSessionId;

    @Override
    public List<FileUpload> getAllFileUpload() {
        ResponseEntity<FileUpload[]> response = restTemplate.getForEntity(hostURL + "/all", FileUpload[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public FileUpload getById(Long id) {
        ResponseEntity<FileUpload> response = restTemplate.getForEntity(hostURL + "/" + id, FileUpload.class);
        return response.getBody();
    }

    @Override
    public HttpStatus addFile(RequestBean requestBean) {
        ResponseEntity<HttpStatus> response = restTemplate.postForEntity(hostURL, requestBean, HttpStatus.class);
        return response.getBody();
    }

    @Override
    public void deleteFileUpload(String fileName) {
        restTemplate.delete(hostURL + "/delete?aSessionId=" + aSessionId + "&fileName=" + fileName);
    }

    @Override
    public ResponseEntity getByFileName(String fileName) {

        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                hostURL + "/fileName?aSessionId="+ aSessionId + "&fileName="+ fileName,
                HttpMethod.GET, entity, byte[].class, "1");
        
        return response;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.service;

import de.exb.platform.cloud.fileservice.model.FileUpload;
import de.exb.platform.cloud.fileservice.repository.FileUploadRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Olakunle Awotunbo
 */
@Service
public class FileUploadServiceImpl implements FileUploadService{

    private final FileUploadRepository fileUploadRepository;

    @Autowired
    public FileUploadServiceImpl(FileUploadRepository fileUploadRepository) {
        this.fileUploadRepository = fileUploadRepository;
    }    
    
    @Override
    public Optional<FileUpload> findByReferenceNumber(String referenceNumber) {
        return fileUploadRepository.findByReferenceNumber(referenceNumber);
    }

    @Override
    public Optional<FileUpload> findByOriginalFileName(String originalFileName) {
        return fileUploadRepository.findByOriginalFileName(originalFileName);
    }

    @Override
    public Optional<FileUpload> findById(Long id) {
        return fileUploadRepository.findById(id);
    }

    @Override
    public FileUpload save(FileUpload fileUpload) {
        return fileUploadRepository.save(fileUpload);
    }

    @Override
    public List<FileUpload> findAll() {
        return fileUploadRepository.findAll();
    }

    @Override
    public Page<FileUpload> findAll(Pageable pageable) {
        return fileUploadRepository.findAll(pageable);
    }

    @Override
    public void delete(FileUpload fileUpload) {
        fileUploadRepository.delete(fileUpload);
    }

    
}

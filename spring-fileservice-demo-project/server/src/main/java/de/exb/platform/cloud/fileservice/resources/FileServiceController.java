package de.exb.platform.cloud.fileservice.resources;

import de.exb.platform.cloud.fileservice.model.FileUpload;
import de.exb.platform.cloud.fileservice.payload.ApiResponse;
import de.exb.platform.cloud.fileservice.service.FileService;
import de.exb.platform.cloud.fileservice.service.FileServiceException;
import de.exb.platform.cloud.fileservice.service.FileUploadService;
import de.exb.platform.cloud.fileservice.service.HelperService;
import io.swagger.annotations.Api;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files") 
@Slf4j
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class FileServiceController {

    private final FileService fileService;
    private final FileUploadService fileUploadService;
    private final HelperService helperService;

    @Autowired
    public FileServiceController(FileService fileService,
            FileUploadService fileUploadService,
            HelperService helperService
    ) {
        this.fileService = fileService;
        this.fileUploadService = fileUploadService;
        this.helperService = helperService;
    }
    
    @GetMapping("/all")
    public List<FileUpload> getAllFiles() {

        return fileUploadService.findAll();
    }
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam(name = "aSessionId", required = true) String aSessionId,
            @RequestParam("file") MultipartFile uploadfile
    ) {

        try {
            log.info("Agents file upload");
            
            if (uploadfile.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "File is empty!"),
                        HttpStatus.BAD_REQUEST);
            }

            
            Map<String, String> map = helperService.fileUpload(uploadfile);
            
            String originalFilename = map.get("originalFilename");
            String referenceNumber = map.get("referenceNumber");
            String batchUploadFileName = map.get("batchUploadFileName");
            
            // save file uploaded
            FileUpload fileUpload = new FileUpload();
            fileUpload.setOriginalFileName(originalFilename);
            fileUpload.setReferenceNumber(referenceNumber);
            fileUpload.setBatchUploadFileName(batchUploadFileName);
            fileUpload.setCreatedAt(helperService.getCurrentDateTime());
            fileUpload.setUpdatedAt(helperService.getCurrentDateTime());
            
            // Save the file
            fileUploadService.save(fileUpload);
            
            return ResponseEntity.ok(new ApiResponse(true, uploadfile.getOriginalFilename() + " Uploaded successfully"));
        } catch (FileServiceException ex) {
            log.error("Error - " + ex);
        } 
         return new ResponseEntity(new ApiResponse(false, "File upload fail!"),
                        HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/uploadMultipleFiles")
    public List<? extends ResponseEntity<?>> uploadMultipleFiles(
             @RequestParam(name = "aSessionId", required = true) String aSessionId,
            @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(aSessionId, file))
                .collect(Collectors.toList());
    }
}

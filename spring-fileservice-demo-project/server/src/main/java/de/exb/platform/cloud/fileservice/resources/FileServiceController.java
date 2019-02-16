package de.exb.platform.cloud.fileservice.resources;

import de.exb.platform.cloud.fileservice.model.FileUpload;
import de.exb.platform.cloud.fileservice.payload.ApiResponse;
import de.exb.platform.cloud.fileservice.service.FileService;
import de.exb.platform.cloud.fileservice.service.FileServiceException;
import de.exb.platform.cloud.fileservice.service.FileUploadService;
import de.exb.platform.cloud.fileservice.service.HelperService;
import io.swagger.annotations.Api;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@Slf4j
@Api(value = "File Service management", description = "Manages file service")
public class FileServiceController {

    private final FileService fileService;
    private final FileUploadService fileUploadService;
    private final HelperService helperService;
    @Value("${app.uploadPath}")
    private String uploadPath;

    @Autowired
    public FileServiceController(FileService fileService,
            FileUploadService fileUploadService,
            HelperService helperService
    ) {
        this.fileService = fileService;
        this.fileUploadService = fileUploadService;
        this.helperService = helperService;
    }

    /**
     * List all uploaded files
     *
     * @return
     */
    @GetMapping("/all")
    public List<FileUpload> getAllFiles() {

        return fileUploadService.findAll();
    }

    /**
     * Get a specific file by name
     *
     * @param aSessionId
     * @param fileName
     * @return
     */
    @GetMapping("/fileName")
    public ResponseEntity<?> getFileName(
            @RequestParam(name = "aSessionId", required = true) String aSessionId,
            @RequestParam(name = "fileName", required = true) String fileName,
            HttpServletRequest request) throws FileServiceException {

        // Check if file exist in Fileupload table
        Optional<FileUpload> fileUploadOp = fileUploadService.findByOriginalFileName(fileName);
        if (fileUploadOp.isPresent()) {

            String serverFileName = uploadPath + File.separator + fileUploadOp.get().getBatchUploadFileName();

            boolean isExist = fileService.exists(aSessionId, serverFileName);
            log.info("isExist :: " + isExist);

            String contentType = null;
            InputStreamResource resource = null;
            File file = null;
            // Check if ile exist on the upload path
            if (isExist) {

                try {
                    file = new File(serverFileName);
                    resource = new InputStreamResource(new FileInputStream(file));
                    contentType = request.getServletContext().getMimeType(file.getAbsolutePath());
                    if (contentType == null) {
                        contentType = "application/octet-stream";
                    }
                } catch (IOException ex) {
                    log.error("Error - " + ex.getMessage());
                }

            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } else {
            return new ResponseEntity(new ApiResponse(false, "File not found"),
                    HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Upload single file
     *  
     * @param aSessionId
     * @param uploadfile
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam(name = "aSessionId", required = true) String aSessionId,
            @RequestParam("file") MultipartFile uploadfile
    ) {

        try {
            
            if (uploadfile.isEmpty()) {
                return new ResponseEntity(new ApiResponse(false, "File is empty!"),
                        HttpStatus.BAD_REQUEST);
            }

            // Check if file exist in Fileupload table
            Optional<FileUpload> fileUploadOp = fileUploadService.findByOriginalFileName(uploadfile.getOriginalFilename());                
                
            if (fileUploadOp.isPresent()) { 
                return new ResponseEntity(new ApiResponse(false, "File name already exist. File name should be unique"),
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
        return new ResponseEntity(new ApiResponse(false, "File upload failed!"),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Upload multiple files
     *
     * @param aSessionId
     * @param files
     * @return
     */
    @PostMapping("/uploadMultipleFiles")
    public List<? extends ResponseEntity<?>> uploadMultipleFiles(
            @RequestParam(name = "aSessionId", required = true) String aSessionId,
            @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(aSessionId, file))
                .collect(Collectors.toList());
    }

    /**
     * Delete file
     * localhost:8080/api/v1/files/delete?aSessionId=csdsdsd&fileName=AgentUsers 1976.xls
     * @param aSessionId
     * @param fileName
     * @return 
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFile(
            @RequestParam(name = "aSessionId", required = true) String aSessionId,
            @RequestParam(name = "fileName", required = true) String fileName) {

        Optional<FileUpload> fileUploadOp = fileUploadService.findByOriginalFileName(fileName);
        boolean isExist = false;
        if (fileUploadOp.isPresent()) {

            String serverFileName = uploadPath + File.separator + fileUploadOp.get().getBatchUploadFileName();

            System.out.println("filePath :: " + serverFileName);

            try {
                isExist = fileService.exists(aSessionId, serverFileName);
                if (isExist){
                    helperService.deleteFile(serverFileName, fileUploadOp.get());
                }
            } catch (FileServiceException ex) {
                log.error("Error - " + ex.getMessage());
            }
            
            return new ResponseEntity(new ApiResponse(true, "File deleted successfully!"),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity(new ApiResponse(false, "File not found"),
                    HttpStatus.NOT_FOUND);
        }

    }
}

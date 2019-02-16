/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.resources;

import java.io.InputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Olakunle Awotunbo
 */
public class FileServiceControllerTest extends AbstractControllerTest {

    private InputStream is;
    String aSessionId = "dfsfsdf32323"; // Dummy session id
    // Filename should be unique to avoid new file overwriting previous file on storage path
    String referenceNumber = String.valueOf(System.currentTimeMillis());
    String fileName = "test_" + referenceNumber + ".txt";
    String deletefileName = fileName;

    @Before
    public void setUp() {
        super.setUp();

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllFiles method, of class FileServiceController.
     */
    @Test
    public void testGetAllFiles() throws Exception {

        String uri = super.hostUrl + "/all";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    /**
     * Test of getFileName method, of class FileServiceController.
     */
    @Test
    public void testGetFileName() throws Exception {
        /*
        String uri = super.hostUrl + "/fileName";
        mvc.perform(get(uri).param("aSessionId", "wddwddsds").param("fileName", "fileName"))
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
         */
    }

    /**
     * Test of uploadFile method, of class FileServiceController.
     */
    @Test
    public void testUploadFile() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName,
                "text/plain", "test data".getBytes());

        String uri = super.hostUrl + "/upload";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart(uri)
                .file(mockMultipartFile)
                .param("aSessionId", aSessionId);
        this.mockMvc.perform(builder).andExpect(status().is(201))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * Test of uploadMultipleFiles method, of class FileServiceController.
     */
    @Test
    public void testUploadMultipleFiles() {
        System.out.println("uploadMultipleFiles");
        /*
        String aSessionId = "";
        MultipartFile[] files = null;
        FileServiceController instance = null;
        List<? extends ResponseEntity<?>> expResult = null;
        List<? extends ResponseEntity<?>> result = instance.uploadMultipleFiles(aSessionId, files);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
         */
    }

    /**
     * Test of deleteFile method, of class FileServiceController.
     */
    @Test
    public void testDeleteFile() throws Exception {
        System.out.println("deleteFile");
        
        String dFile = "test_1550337839810.txt";
        String uri = super.hostUrl + "/delete?aSessionId=" + aSessionId + "&fileName=" + dFile;
        System.out.println("uri :: " + uri);
              
        this.mockMvc.perform(MockMvcRequestBuilders
            .delete(uri)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        
    }

}

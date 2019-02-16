/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.exb.platform.cloud.fileservice.resources;

import java.io.InputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author Olakunle Awotunbo
 */
public class FileServiceControllerTest extends AbstractControllerTest {

    private InputStream is;
    String aSessionId = ""; // Dummy session id
    String fileName = "";
    
    @Before
    public void setUp() {
        super.setUp();
        aSessionId = "dfsfsdf32323"; // Dummy session id
        fileName = "test.txt";

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
        String uri = super.hostUrl + "/fileName?aSessionId=" + aSessionId + "&fileName=" + fileName;
        
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
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
     * Test of deleteFile method, of class FileServiceController.
     */
    @Test
    public void testDeleteFile() throws Exception {
        System.out.println("deleteFile");

        String uri = super.hostUrl + "/delete?aSessionId=" + aSessionId + "&fileName=" + fileName;
        System.out.println("uri :: " + uri);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete(uri)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}

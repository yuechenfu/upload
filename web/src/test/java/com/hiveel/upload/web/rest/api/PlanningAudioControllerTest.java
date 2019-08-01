package com.hiveel.upload.web.rest.api;

import com.google.gson.reflect.TypeToken;
import com.hiveel.core.model.rest.BasicRestCode;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.web.cache.FolderCache;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanningAudioControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void image() throws Exception {
        String base64 = "data:audio/wav;base64,UklGRigAAABXQVZFZm10IBIAAAABAAEARKwAAIhYAQACABAAAABkYXRhAgAAAAEA";
        String fileName = "ex0008";
        String saveFolder = "ISSUE";

        MvcResult mvcResult = this.mvc.perform(post("/api/audio").header("apiKey", "hiveel")
                .param("base64", base64)
                .param("saveFolder", saveFolder)
                .param("saveFor", "uxx123")
                .param("fileNameWithoutType", fileName)
                .param("single", "false"))
                .andExpect(status().isOk())
                .andReturn();
        Rest<String> rest = Rest.fromJson(mvcResult.getResponse().getContentAsString(), BasicRestCode.class, new TypeToken<Rest<String>>() {}.getType());
        Assert.assertEquals(BasicRestCode.SUCCESS,rest.getCode());

        //上传之后验证上传了的文件是否真的存在
        String url = rest.getData();
        url = url.substring(0, url.indexOf("?")==-1?url.length():url.indexOf("?"));
        String realPath = FolderCache.getUploadRootDir();
        File uploaded = new File(realPath+url);
        Assert.assertEquals(true,uploaded.exists());
        File dir = new File(realPath+"upload");
        if(dir.exists()){
          FileUtils.forceDelete(dir);
        }
    }


    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

}

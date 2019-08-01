package com.hiveel.upload.web.rest.mg;

import com.google.gson.reflect.TypeToken;
import com.hiveel.auth.sdk.model.Account;
import com.hiveel.core.model.rest.BasicRestCode;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.manager.AuthManager;
import com.hiveel.upload.web.cache.FolderCache;
import com.hiveel.upload.web.interceptor.AuthInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PlanningDeleteControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    @Test
    public void delete() throws Exception {
        String base64 = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE0AAABMCAMAAAAfi8onAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAABsFBMVEUAAAAYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzS"
                + "QYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQYzSQAAAA/dV94AAAAjnRSTlMAFD5uoMXi7/Qyjcw0p/zp2dHOEon+u35QLhMGNo8JkFjt+pUklmX3SEnatx4fuDOrDxCtEc+2itwaG971+EaplzEiJwjqspmR0Nc9vPEHv"
                + "W9XgKFOtfJVTw6vMAULrBV6s/PSJlJWAlFbuQyq+V9dTbrwvn9gXv2OZETbGagctEXnhyn7d+XVzcpm3Yju8qbbJAAAAAFiS0dEAIgFHUgAAAAJcEhZcwAACxIAAAsSAdLdfvwAAASuSURBVFjD7ZfrW9RGFIeDCrsS2AWRgQWhiOIWLI3YQXtZpAY0iNZqLU1paSn0XoL2fonWehu1mcz5mzsns9nNshuSoB/6gfM8gbn85t0z9zOatqu1HTh4qL0jl891tB86eKBNexE73KlD1PTOw3tmdXXnEVEo9vQe6e0pFjCT7+7aE6vvKLL6ycBgaWhYGx4qDQ6QfuQd7csOO4Z9LI6URiNlo6VXitjfY1lhY8cBxk+cbCo/OTEOcHwsG+xUGaDn1ZZVk1MA5VOZPJOw06/FVE6/LnEZvDNy"
                + "AGdmYqvPvgGQM9LC6CzAuZldBGdPA8zSlLTzAG++tavi7XcAzqeDdVVg7kKC5sIcVNIt43mAiUTRuwDzaWAXK1A0Y2tHFxaD/+YlqFxMQesEGImtHLoMlkotAXQmw9quQKEUV0kvAyyrZKkfriQfUFfH4dpwTF3fOYD3BlX6+vswfjWRdgNgIA4mPavcDHMfANxIpN2Cwocx3VwB+MiuZT8uwK1EWjsUWw/b6ifybBur5z+9BO2JtA7oCQd3LVr+2ecSZkcK1qegI5GWg141CWtfbES6vCphmzejwsUvIZdIq8ARlfhqE8pf1zw7AzD3TYPw21mopPdtRp47m99ptXT++0ZhKt/q49b3g8QF3m05ANu3dwhTjVtkTtskLi+9m8HZvLNTmGpOo+ttiMi1/+Nitxyz203CVOutYS8M/QTw"
                + "8y9yB9xpFv6aZi807tN1ghd8+bdm3fVrafbpjjOkT3q3/XsLXemPNGcInm9/RrLrztRfrWQjqc63prN3bb2VKu3Zi/fCiUTRRMp7Ae+sspugcVPfWXif3r23q+Le3dT3aXDXr6zuIlhdyXDXB3HI3/G41fkscYiKkTbux1Te/ydbjKTit+UHLaseLGeN31RsObc03VQ+vTSXPbasxr0PHz2O3q3Djx893FPcG8bkTzbY5MKWzG4tTLKNJ3uNybXae2H76bPn/z5/9nT7Rd4LaC/zLYP2Mt9Z+7Zv+7Zv/yuzaxEOtfBCpupWZp4q9GTWDE2mGa+bj21czQ7z8rXh8BBnAqaYCGIUrh6WlHMzUAsh/+hUM72aWVwKfGFqtu4bniMcbE5CnKJV8VWaRqu/FjRFkVfrlRcUEV16oAsgKlAyO"
                + "fGVp8A5RXecCC2Q0zrNA7ORZnJsLWTboJ1mWqaLo2KAEShtbFCnUYrNYmhUDqbFPc/HD0zXsgyNChb2FIc66ClRKBz5RpoRzgnjOClCtzim5Yc0XXrBwFY0OeoimFSuWhMQtqIJy7IY0urmKA8tnCD80BsL++To4SyorlABQchFDfB20Gq+mdX+otso8Wo0U/Y1SnOBs+pEe1rMuIWjhz9E8KvRNF9voPlcFiTRKKfNNNWM1mmmLafFFF4SLWjAQA96SlUVxxnQTFczQppOLDkVFv4y0gw/QrOhHs57UmXKpaYjjQTzxwThBm4oHqxdSSOcSYeqq9gEJvQIjXLhq43FLOFoBucuEQ5jDiPEt105/I4gzJM55nlI416w/DRXqiVNcFyehCMBccwKTaoYkf3xLF1tJsvDhq4fCqg8FZiCIU"
                + "6eEoRETo0MgXp9dOubamfVf+BO7Gi+VZjRAAAAAElFTkSuQmCC";
        String fileName = "ex0001";
        String saveFolder = "ISSUE";
        Account account = new Account.Builder().set("id",1L).set("extra","VE").build();
        Mockito.when(authManager.findAccountByToken(Mockito.anyString())).thenReturn(account);
        MvcResult mvcResult = this.mvc.perform(post("/mg/image").header("authorization", "Bearer 123456778")
                .param("base64", base64)
                .param("saveFolder", saveFolder)
                .param("saveFor", "uxx123")
                .param("fileNameWithoutType", fileName)
                .param("width", "300")
                .param("height", "0")
                .param("single", "false"))
                .andExpect(status().isOk())
                .andReturn();
        Rest<String> rest = Rest.fromJson(mvcResult.getResponse().getContentAsString(), BasicRestCode.class, new TypeToken<Rest<String>>() {}.getType());
        Assert.assertEquals(BasicRestCode.SUCCESS,rest.getCode());

        //上传之后验证上传了的文件是否真的存在
        String url = rest.getData();
        url = url.substring(0, url.indexOf("?")==-1?url.length():url.indexOf("?"));
        String realPath = FolderCache.getUploadRootDir();
        File file = new File(realPath+url);
        Assert.assertEquals(true,file.exists());

        //请求删除
        this.mvc.perform(MockMvcRequestBuilders.delete("/mg/file")
                .header("authorization", "Bearer 123456778")
                .param("saveFolder", saveFolder)
                .param("fileName", rest.getData()))
                .andExpect(status().isOk())
                .andReturn();
        //验证删除结果，文件应该不再存在
        Assert.assertEquals(false,file.exists());
    }

    @Autowired
    private AuthInterceptor authInterceptor;

    private AuthManager authManager= Mockito.mock(AuthManager.class);

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
        authInterceptor.setAuthManager(authManager);
    }

}

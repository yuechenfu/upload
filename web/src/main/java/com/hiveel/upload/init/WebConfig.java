package com.hiveel.upload.init;

import com.hiveel.upload.web.cache.FolderCache;
import com.hiveel.upload.web.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private ApiKeyAuthorityInterceptor apiKeyAuthorityInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private MgInterceptor mgInterceptor;
    @Autowired
    private DrInterceptor drInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiKeyAuthorityInterceptor).addPathPatterns("/api/**").order(1);
        registry.addInterceptor(authInterceptor).addPathPatterns("/mg/**").addPathPatterns("/dr/**").order(1);
        registry.addInterceptor(mgInterceptor).addPathPatterns("/mg/**").order(2);
        registry.addInterceptor(mgInterceptor).addPathPatterns("/mc/**").order(2);
        registry.addInterceptor(drInterceptor).addPathPatterns("/dr/**").order(2);
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imagePathRoot = "file:"+FolderCache.getUploadRootDir();
        registry.addResourceHandler("/static/**").addResourceLocations(imagePathRoot) ;
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*");
    }
}

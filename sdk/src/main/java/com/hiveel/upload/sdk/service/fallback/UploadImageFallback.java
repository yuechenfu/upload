package com.hiveel.upload.sdk.service.fallback;

import com.hiveel.core.log.util.LogUtil;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.sdk.model.UploadImage;
import com.hiveel.upload.sdk.service.UploadImageService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UploadImageFallback implements FallbackFactory<UploadImageService> {

    @Override
    public UploadImageService create(Throwable throwable) {
        LogUtil.error("", throwable);
        Rest<String> rest = Rest.createFail();
        return new UploadImageService() {
            @Override
            public Rest<String> uploadBySystem(String apiKey, UploadImage uploadImage) {
                return rest;
            }
        };
    }
}

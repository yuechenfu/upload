package com.hiveel.upload.sdk.service.fallback;

import com.hiveel.core.log.util.LogUtil;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.sdk.model.UploadAudio;
import com.hiveel.upload.sdk.service.UploadDeleteService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UploadDeleteFallback implements FallbackFactory<UploadDeleteService> {

    @Override
    public UploadDeleteService create(Throwable throwable) {
        LogUtil.error("", throwable);
        Rest<String> rest = Rest.createFail();
        return new UploadDeleteService() {
            @Override
            public Rest<String> deleteBySystem(String apiKey, UploadAudio uploadAudio) {
                return rest;
            }
        };
    }
}

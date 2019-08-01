package com.hiveel.upload.sdk.service.fallback;

import com.hiveel.core.log.util.LogUtil;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.sdk.model.UploadAudio;
import com.hiveel.upload.sdk.service.UploadAudioService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UploadAudioFallback implements FallbackFactory<UploadAudioService> {

    @Override
    public UploadAudioService create(Throwable throwable) {
        LogUtil.error("", throwable);
        Rest<String> rest = Rest.createFail();
        return new UploadAudioService() {
            @Override
            public Rest<String> upload(String userId, UploadAudio uploadAudio) {
                return rest;
            }
        };
    }
}

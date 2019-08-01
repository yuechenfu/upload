package com.hiveel.upload.sdk.service;

import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.sdk.model.UploadAudio;
import com.hiveel.upload.sdk.service.fallback.UploadAudioFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "upload-audio", url = "${freitx.uploadServer}", fallbackFactory = UploadAudioFallback.class)
public interface UploadAudioService {

    @PostMapping(value = "/api/audio", consumes = {"application/x-www-form-urlencoded"})
    Rest<String> upload(@RequestHeader(name = "apiKey") String apiKey, UploadAudio uploadAudio);

}

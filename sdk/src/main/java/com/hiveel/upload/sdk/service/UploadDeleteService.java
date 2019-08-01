package com.hiveel.upload.sdk.service;

import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.sdk.model.UploadAudio;
import com.hiveel.upload.sdk.service.fallback.UploadDeleteFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "upload-delete", url = "${freitx.uploadServer}", fallbackFactory = UploadDeleteFallback.class)
public interface UploadDeleteService {

    @DeleteMapping(value = "/api/file", consumes = {"application/x-www-form-urlencoded"})
    Rest<String> deleteBySystem(@RequestHeader(name = "apiKey") String apiKey, UploadAudio uploadAudio);
}

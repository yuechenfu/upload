package com.hiveel.upload.sdk.service;

import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.sdk.model.UploadImage;
import com.hiveel.upload.sdk.service.fallback.UploadImageFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "upload-image", url = "${freitx.uploadServer}", fallbackFactory = UploadImageFallback.class)
public interface UploadImageService {

    @PostMapping(value = "/api/image", consumes = {"application/x-www-form-urlencoded"})
    Rest<String> uploadBySystem(@RequestHeader(name = "apiKey") String apiKey, UploadImage uploadImage);

}

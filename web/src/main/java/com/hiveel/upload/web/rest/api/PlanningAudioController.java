package com.hiveel.upload.web.rest.api;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.util.ParameterExceptionUtil;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.model.PlanningAudio;
import com.hiveel.upload.model.PlanningImage;
import com.hiveel.upload.service.PlanningAudioService;
import com.hiveel.upload.service.PlanningImageService;
import com.hiveel.upload.web.cache.FolderCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController("planningAudioControllerApi")
@RequestMapping("/api")
public class PlanningAudioController {
    @Autowired
    private PlanningAudioService service;

    @RequestMapping(value = "/audio", method = RequestMethod.POST)
    public Rest<String> image(PlanningAudio planningAudio) throws ParameterException, IOException {
        ParameterExceptionUtil.verify("planningAudio.saveFolder", planningAudio.getSaveFolder() ).beContainedIn(FolderCache.getStaticList());
        ParameterExceptionUtil.verify("planningImage.saveFor", planningAudio.getSaveFor()).isNotEmpty();
        ParameterExceptionUtil.verify("planningImage.type", planningAudio.getType()).isNotEmpty();
        ParameterExceptionUtil.verify("planningImage.base64", planningAudio.getBase64()).isNotEmpty().isLengthIn(60, -1).contain("data:", ";base64,");
        planningAudio.correctFileNameWithoutType();
        service.upload(planningAudio);
        return Rest.createSuccess(planningAudio.getUrl());
    }
}

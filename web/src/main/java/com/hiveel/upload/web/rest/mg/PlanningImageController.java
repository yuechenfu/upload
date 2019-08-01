package com.hiveel.upload.web.rest.mg;

import com.hiveel.auth.sdk.model.Account;
import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.util.ParameterExceptionUtil;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.model.PlanningImage;
import com.hiveel.upload.service.PlanningImageService;
import com.hiveel.upload.web.cache.FolderCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@RestController("planningImageControllerMg")
@RequestMapping({"/mg","/mc"})
public class PlanningImageController {
    @Autowired
    private PlanningImageService service;

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public Rest<String> image(@RequestAttribute("loginAccount") Account loginAccount, PlanningImage planningImage) throws ParameterException, IOException {
        ParameterExceptionUtil.verify("planningAudio.saveFolder", planningImage.getSaveFolder() ).beContainedIn(FolderCache.getStaticList());
        ParameterExceptionUtil.verify("planningImage.saveFor", planningImage.getSaveFor()).isNotEmpty();
        ParameterExceptionUtil.verify("planningImage.type", planningImage.getType()).isNotEmpty();
        ParameterExceptionUtil.verify("planningImage.base64", planningImage.getBase64()).isNotEmpty().isLengthIn(60, -1).contain("data:", ";base64,");
        planningImage.correctFileNameWithoutType();
        service.upload(planningImage);
        return Rest.createSuccess(planningImage.getUrl());
    }
}

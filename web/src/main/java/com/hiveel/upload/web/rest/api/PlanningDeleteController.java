package com.hiveel.upload.web.rest.api;

import com.hiveel.auth.sdk.model.Account;
import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.UnauthorizationException;
import com.hiveel.core.exception.util.ParameterExceptionUtil;
import com.hiveel.core.log.LogLevel;
import com.hiveel.core.model.rest.Rest;
import com.hiveel.upload.model.PlanningDelete;
import com.hiveel.upload.service.PlanningDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

@RestController("planningDeleteControllerApi")
@RequestMapping("/api")
public class PlanningDeleteController {
    @Autowired
    private PlanningDeleteService service;

    @RequestMapping(value = "/file", method = RequestMethod.DELETE)
    public Rest<Boolean> delete(PlanningDelete planningDelete) throws ParameterException, UnauthorizationException {
        ParameterExceptionUtil.verify("planningDelete.fileName", planningDelete.getFileName()).isNotEmpty();
        boolean data = service.delete(planningDelete);
        return Rest.createSuccess(data);
    }

}

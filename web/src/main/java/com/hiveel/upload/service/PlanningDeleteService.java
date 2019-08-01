package com.hiveel.upload.service;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.UnauthorizationException;
import com.hiveel.upload.model.PlanningDelete;

public interface PlanningDeleteService {

	boolean delete(PlanningDelete planningDelete) throws ParameterException, UnauthorizationException;

}

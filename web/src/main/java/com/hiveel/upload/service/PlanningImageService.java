package com.hiveel.upload.service;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.upload.model.PlanningImage;

import java.io.IOException;

public interface PlanningImageService {

	/**
	 * @param planningImage
	 * @throws ParameterException
	 * @throws IOException
	 */
	void upload(PlanningImage planningImage) throws ParameterException, IOException;


}

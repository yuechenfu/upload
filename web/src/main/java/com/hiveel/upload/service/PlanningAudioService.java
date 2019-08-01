package com.hiveel.upload.service;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.upload.model.PlanningAudio;

import java.io.IOException;

public interface PlanningAudioService {

	void upload(PlanningAudio planningAudio) throws ParameterException, IOException;

}

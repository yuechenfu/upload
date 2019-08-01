package com.hiveel.upload.service.impl;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.util.ParameterExceptionUtil;
import com.hiveel.upload.service.PlanningAudioService;
import com.hiveel.upload.model.PlanningAudio;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service("planningAudioService")
public class PlanningAudioServiceImpl implements PlanningAudioService {

	@Override
	public void upload(PlanningAudio planningAudio) throws  IOException {
		planningAudio.correctFileNameWithoutType();
		planningAudio.createUrl();
		if (planningAudio.getMultipartFile() != null) {
			uploadMultipartFile(planningAudio);
		} else if (planningAudio.getFile() != null) {
			uploadFile(planningAudio);
		} else {
			uploadBase64(planningAudio);
		}
	}

	private void uploadMultipartFile(PlanningAudio planningAudio) throws IOException {
		FileUtils.writeByteArrayToFile(new File(planningAudio.getAudioName()), planningAudio.getMultipartFile().getBytes());
	}

	private void uploadFile(PlanningAudio planningAudio) throws IOException {
		byte[] byteArray = Files.readAllBytes(Paths.get(planningAudio.getFile().getAbsolutePath()));
		FileUtils.writeByteArrayToFile(new File(planningAudio.getAudioName()), byteArray);
	}

	private void uploadBase64(PlanningAudio planningAudio) throws IOException {
		decodeBase64(planningAudio.getAudioName(), planningAudio.getBase64Data());
	}
	private void decodeBase64(String fileName, String data) throws IOException {
		byte[] decode = Base64.getDecoder().decode(data);
		FileUtils.writeByteArrayToFile(new File(fileName), decode);
	}
}

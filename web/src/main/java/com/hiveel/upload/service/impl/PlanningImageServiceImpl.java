package com.hiveel.upload.service.impl;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.util.ParameterExceptionUtil;

import com.hiveel.upload.service.PlanningImageService;
import com.hiveel.upload.model.PlanningImage;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

@Service("planningImageService")
public class PlanningImageServiceImpl implements PlanningImageService {

    @Override
    public void upload(PlanningImage planningImage) throws IOException {
        planningImage.correctFileNameWithoutType();
        planningImage.createUrl();
        if (planningImage.getMultipartFile() != null) {
            uploadMultipartFile(planningImage);
        } else if (planningImage.getFile() != null) {
            uploadFile(planningImage);
        } else {
            uploadBase64(planningImage);
        }
        if (planningImage.isSingle()) {
            new File(planningImage.getBigName()).delete();
        }
    }

    private void uploadBase64(PlanningImage planningImage) throws IOException {
        if (planningImage.getWidth() > 0 || planningImage.getHeight() > 0) {
            decodeBase64(planningImage.getBigName(), planningImage.getBase64Data());
            planningImage.setFile(new File(planningImage.getBigName()));
            compressByThumbnail(planningImage);
        } else {
            decodeBase64(planningImage.getSmallName(), planningImage.getBase64Data());
        }
    }

    private void decodeBase64(String fileName, String data) throws IOException {
        byte[] decode = Base64.getDecoder().decode(data);
        FileUtils.writeByteArrayToFile(new File(fileName), decode);
    }

    private void uploadMultipartFile(PlanningImage planningImage) throws IOException {
        if (planningImage.getWidth() > 0 || planningImage.getHeight() > 0) {
            File file = new File(planningImage.getBigName());
            FileUtils.writeByteArrayToFile(file, planningImage.getMultipartFile().getBytes());
            planningImage.setFile(file);
            compressByThumbnail(planningImage);
        } else {
            FileUtils.writeByteArrayToFile(new File(planningImage.getSmallName()), planningImage.getMultipartFile().getBytes());
        }
    }

    private void uploadFile(PlanningImage planningImage) throws IOException {
        byte[] byteArray = Files.readAllBytes(Paths.get(planningImage.getFile().getAbsolutePath()));
        if (planningImage.getWidth() > 0 || planningImage.getHeight() > 0) {
            FileUtils.writeByteArrayToFile(new File(planningImage.getBigName()), byteArray);
            compressByThumbnail(planningImage);
        } else {
            FileUtils.writeByteArrayToFile(new File(planningImage.getSmallName()), byteArray);
        }
    }

    private void compressByThumbnail(PlanningImage planningImage) throws IOException {
        Thumbnails.of(planningImage.getFile()).size(planningImage.getWidth(), planningImage.getHeight()).outputFormat(planningImage.getType())
                .toFile(planningImage.getSmallName());
    }
}

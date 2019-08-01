package com.hiveel.upload.model;

import com.hiveel.core.util.DateUtil;
import com.hiveel.core.util.StringUtil;
import com.hiveel.upload.web.cache.FolderCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class PlanningImage implements Serializable {
    private static final long serialVersionUID = 6770260000635903264L;
    // 文件，设置一个
    private File file;
    private MultipartFile multipartFile;
    private String base64;
    // 存储地点
    private String saveFor;
    private String saveFolder;
    // 指定文件名
    private String fileNameWithoutType;
    // 压缩选项
    private int width;
    private int height;
    // 是否一张图片
    private boolean single;
    // 自动获取
    private String base64Data;
    private String realPath;
    private String type;

    // 文件地址
    private String url;

    public void createDateName() {
        this.fileNameWithoutType = "/" + DateUtil.generateFileNameInstance() + StringUtil.generateRandomString(5);
    }

    public String getSmallName() {
        String smallName = getRealPath() + "/upload" + "/" + saveFolder + "/" + saveFor + fileNameWithoutType + "." + type;
        return smallName.toLowerCase();
    }

    public String getBigName() {
        String bigName = getRealPath() + "/upload" + "/" + saveFolder + "/" + saveFor + "/big" + fileNameWithoutType + "." + type;
        return bigName.toLowerCase();
    }

    public String getSmallNameWithoutRealPath() {
        String smallName =  "/upload" + "/" + saveFolder + "/" + saveFor + fileNameWithoutType + "." + type;
        return smallName.toLowerCase();
    }

    public void createUrl() {
        long length = multipartFile != null ? multipartFile.getSize() :
                file != null ? file.length() :
                        base64Data != null ? base64Data.length()
                                : 0;
        url = "/upload" + "/" + saveFolder + saveFor + fileNameWithoutType + "." + type + "?" + length;
        url = url.toLowerCase();
    }

    public void correctFileNameWithoutType() {
        if (StringUtils.isEmpty(fileNameWithoutType)) {
            createDateName();
        }
        fileNameWithoutType = fileNameWithoutType.indexOf("/") == 0 ? fileNameWithoutType : ("/" + fileNameWithoutType);
    }

    public void correctSaveForByEmployee() {
        saveFor = saveFor != null ? saveFor : "";
    }

    public static class Builder {
        private File file;
        private String base64;
        private String base64Data;
        private String saveFor;
        private String saveFolder;
        private String realPath;
        private String fileNameWithoutType;
        private String type;
        private int width;
        private int height;
        private boolean single;

        public Builder(File file, String saveFor, String saveFolder) {
            this.saveFor = saveFor.length() == 0 || saveFor.indexOf("/") == 0 ? saveFor : ("/" + saveFor);
            this.saveFolder = saveFolder;
            this.file = file;
            this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        }

        public Builder(String base64, String saveFor, String saveFolder) {
            this.saveFor = saveFor.length() == 0 || saveFor.indexOf("/") == 0 ? saveFor : ("/" + saveFor);
            this.saveFolder = saveFolder;
            this.base64 = base64;
            int endIndex = this.base64.indexOf(";base64,");
            if (this.base64.indexOf("data:") == 0 && endIndex < 20) {
                this.type = this.base64.substring(this.base64.indexOf("/") + 1, endIndex);
                this.base64Data = this.base64.substring(endIndex + 8);
            }
        }

        public Builder realPath(String realPath) {
            this.realPath = realPath;
            return this;
        }

        public Builder randomName() {
            this.fileNameWithoutType = "/" + StringUtil.generateRandomString(10);
            return this;
        }

        public Builder dateRandomName() {
            this.fileNameWithoutType = "/" + DateUtil.generateFileNameInstance() + StringUtil.generateRandomString(5);
            return this;
        }

        public Builder fileNameWithoutType(String fileNameWithoutType) {
            this.fileNameWithoutType = fileNameWithoutType;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder single(boolean single) {
            this.single = single;
            return this;
        }

        public PlanningImage build() {
            return new PlanningImage(this);
        }
    }

    public PlanningImage() {
    }

    private PlanningImage(Builder builder) {
        this.file = builder.file;
        this.base64 = builder.base64;
        this.base64Data = builder.base64Data;
        this.saveFor = builder.saveFor;
        this.saveFolder = builder.saveFolder;
        this.realPath = builder.realPath;
        this.fileNameWithoutType = builder.fileNameWithoutType;
        this.type = builder.type;
        this.width = builder.width > 0 ? builder.width : builder.height;
        this.height = builder.height > 0 ? builder.height : builder.width;
        this.single = builder.single;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        this.type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    public String getFileNameWithoutType() {
        return fileNameWithoutType;
    }

    public void setFileNameWithoutType(String fileNameWithoutType) {
        this.fileNameWithoutType = fileNameWithoutType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = StringUtils.isNotEmpty(type) ? type : this.type;
    }

    public int getWidth() {
        return width > 0 ? width : height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height > 0 ? height : width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
        int endIndex = this.base64.indexOf(";base64,");
        if (this.base64.indexOf("data:") == 0 && endIndex < 20) {
            this.type = this.base64.substring(this.base64.indexOf("/") + 1, endIndex);
            this.base64Data = this.base64.substring(endIndex + 8);
        }
    }

    public String getBase64Data() {
        return base64Data;
    }

    public void setBase64Data(String base64Data) {
        this.base64Data = base64Data;
    }

    public String getSaveFor() {
        return saveFor;
    }

    public void setSaveFor(String saveFor) {
        this.saveFor = saveFor.length() == 0 || saveFor.indexOf("/") == 0 ? saveFor : ("/" + saveFor);
    }

    public String getSaveFolder() {
        return saveFolder;
    }

    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
        this.type = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
    }

    public String getRealPath() {
        return FolderCache.getUploadRootDir();
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "PlanningImage [" + (file != null ? "file=" + file + ", " : "") + (multipartFile != null ? "multipartFile=" + multipartFile + ", " : "")
                + (base64 != null ? "base64=" + base64 + ", " : "") + (saveFor != null ? "saveFor=" + saveFor + ", " : "")
                + (saveFolder != null ? "saveFolder=" + saveFolder + ", " : "") + (fileNameWithoutType != null ? "fileNameWithoutType=" + fileNameWithoutType + ", " : "")
                + "width=" + width + ", height=" + height + ", single=" + single + ", " + (base64Data != null ? "base64Data=" + base64Data + ", " : "")
                + (realPath != null ? "realPath=" + realPath + ", " : "") + (type != null ? "type=" + type : "") + "]";
    }
}

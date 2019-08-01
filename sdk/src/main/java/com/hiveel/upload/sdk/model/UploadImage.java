package com.hiveel.upload.sdk.model;

import java.io.File;

public class UploadImage {
    private File file;
    private String base64;
    private String saveFor;
    private String saveFolder;
    private String fileNameWithoutType;
    private int width;
    private int height;
    private boolean single;
    private String token;

    public static Builder file(File file, String saveFor, String saveFolder) {
        return new Builder(file, saveFor, saveFolder);
    }

    public static Builder base64(String base64, String saveFor, String saveFolder) {
        return new Builder(base64, saveFor, saveFolder);
    }

    public static class Builder {
        private File file;
        private String base64;
        private String saveFor;
        private String saveFolder;
        private String fileNameWithoutType;
        private int width;
        private int height;
        private boolean single;
        private String token;

        private Builder(File file, String saveFor, String saveFolder) {
            this.file = file;
            this.saveFor = saveFor;
            this.saveFolder = saveFolder;
        }

        private Builder(String base64, String saveFor, String saveFolder) {
            this.base64 = base64;
            this.saveFor = saveFor;
            this.saveFolder = saveFolder;
        }

        public Builder fileNameWithoutType(String fileNameWithoutType) {
            this.fileNameWithoutType = fileNameWithoutType;
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

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public UploadImage build() {
            return new UploadImage(this);
        }
    }

    private UploadImage(Builder builder) {
        this.file = builder.file;
        this.base64 = builder.base64;
        this.saveFor = builder.saveFor;
        this.saveFolder = builder.saveFolder;
        this.fileNameWithoutType = builder.fileNameWithoutType;
        this.width = builder.width;
        this.height = builder.height;
        this.single = builder.single;
        this.token = builder.token != null ? builder.token : builder.saveFor;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getFileNameWithoutType() {
        return fileNameWithoutType;
    }

    public void setFileNameWithoutType(String fileNameWithoutType) {
        this.fileNameWithoutType = fileNameWithoutType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSaveFor() {
        return saveFor;
    }

    public void setSaveFor(String saveFor) {
        this.saveFor = saveFor;
    }

    public String getSaveFolder() {
        return saveFolder;
    }

    public void setSaveFolder(String saveFolder) {
        this.saveFolder = saveFolder;
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

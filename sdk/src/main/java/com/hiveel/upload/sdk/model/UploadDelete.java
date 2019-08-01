package com.hiveel.upload.sdk.model;

public class UploadDelete {
    private String fileName;
    private String saveFor;

    public UploadDelete(String fileName, String saveFor) {
        this.fileName = fileName;
        this.saveFor = saveFor;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSaveFor() {
        return saveFor;
    }

    public void setSaveFor(String saveFor) {
        this.saveFor = saveFor;
    }

}

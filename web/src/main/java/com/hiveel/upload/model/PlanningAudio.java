package com.hiveel.upload.model;

import com.hiveel.core.util.DateUtil;
import com.hiveel.core.util.StringUtil;
import com.hiveel.upload.web.cache.FolderCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

public class PlanningAudio implements Serializable {
	private static final long serialVersionUID = -5836002305914774997L;
	private File file;
	private MultipartFile multipartFile;
	private String base64;
	private String base64Data;
	private String saveFor;
	private String saveFolder;
	private String realPath;
	private String fileNameWithoutType;
	private String type;
	
	private String url;
	
	public void createDateName() { 
		this.fileNameWithoutType = "/" + DateUtil.generateFileNameInstance() + StringUtil.generateRandomString(5); 
	}
	
	public String getAudioName() {
		return getRealPath() + "/upload" + saveFor + "/"+ saveFolder + fileNameWithoutType + "." + type;
	}
	public void createUrl() {
		url = "/upload" + saveFor +  "/"+ saveFolder  + fileNameWithoutType + "." + type;
	}

	public void correctFileNameWithoutType() {
		if (StringUtils.isEmpty(fileNameWithoutType)) {
			createDateName();
		}
		fileNameWithoutType = fileNameWithoutType.indexOf("/") == 0 ? fileNameWithoutType : ("/" + fileNameWithoutType);
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
		
		public void createRandomName() { 
			this.fileNameWithoutType = "/" + StringUtil.generateRandomString(10); 
		}
		public void createDateName() { 
			this.fileNameWithoutType = "/" + DateUtil.generateFileNameInstance() + StringUtil.generateRandomString(5); 
		}
		
		public Builder(File file, String saveFor, String saveFolder) {
			this.saveFor = saveFor.length()==0 || saveFor.indexOf("/")==0 ? saveFor : ("/"+saveFor);
			this.saveFolder = saveFolder;
			this.file = file;
			this.type = file.getName().substring(file.getName().lastIndexOf(".")+1);
		}
		public Builder(String base64, String saveFor, String saveFolder) {
			this.saveFor = saveFor.length()==0 || saveFor.indexOf("/")==0 ? saveFor : ("/"+saveFor);
			this.saveFolder = saveFolder;
			this.base64 = base64;
			int endIndex = this.base64.indexOf(";base64,");
			if (this.base64.indexOf("data:") == 0 && endIndex < 20) {
				this.type = this.base64.substring(this.base64.indexOf("/") + 1, endIndex);
				this.base64Data = this.base64.substring(endIndex + 8);
			}
		}
		public Builder realPath(String realPath) { this.realPath = realPath; return this; }
		public Builder randomName() { this.fileNameWithoutType = "/" + StringUtil.generateRandomString(10); return this; }
		public Builder dateRondomName() { this.fileNameWithoutType = "/" + DateUtil.generateFileNameInstance() + StringUtil.generateRandomString(5); return this; }
		public Builder fileNameWithoutType(String fileNameWithoutType) { this.fileNameWithoutType = fileNameWithoutType; return this; }
		public Builder type(String type) { this.type = type; return this; }
		public PlanningAudio build() { return new PlanningAudio(this); }
	}
	public PlanningAudio() {}
	private PlanningAudio(Builder builder) {
		this.file = builder.file;
		this.base64 = builder.base64;
		this.base64Data = builder.base64Data;
		this.saveFor = builder.saveFor;
		this.saveFolder = builder.saveFolder;
		this.realPath = builder.realPath;
		this.fileNameWithoutType = builder.fileNameWithoutType;
		this.type = builder.type;
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
		this.type = file.getName().substring(file.getName().lastIndexOf(".")+1);
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
		this.saveFor = saveFor.length()==0 || saveFor.indexOf("/")==0 ? saveFor : ("/"+saveFor);
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
		this.type = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
	}

	public String getRealPath() {
		return FolderCache.getUploadRootDir();
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	public String getUrl() {
		return url;
	}
}

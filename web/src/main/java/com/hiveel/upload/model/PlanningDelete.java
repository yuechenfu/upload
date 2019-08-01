package com.hiveel.upload.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hiveel.upload.web.cache.FolderCache;

import java.io.Serializable;


public class PlanningDelete implements Serializable {
	private static final long serialVersionUID = 3152572230512795582L;
	private String saveFor;
	private String fileName;
	private String realPath;
	
	public String getFullPath() {
		correctPath();
		return getRealPath() + fileName;
	}
	public String getBigFullPath() {
		correctPath();
		return realPath + fileName.substring(0, fileName.lastIndexOf("/")) + "/big" + fileName.substring(fileName.lastIndexOf("/"));
	}
	private void correctPath() {
		if (fileName.contains("?")) {
			fileName = fileName.substring(0, fileName.lastIndexOf("?"));
		}
	}
	
	public static class Builder {
		private String saveFor;
		private String fileName;
		private String realPath;
		public Builder saveFor(String saveFor) { this.saveFor = saveFor.length()==0 || saveFor.indexOf("/")==0 ? saveFor : ("/"+saveFor); return this; }
		public Builder fileName(String fileName) { this.fileName=fileName; return this; }
		public Builder realPath(String realPath) { this.realPath=realPath; return this; }
		public PlanningDelete build() { return new PlanningDelete(this); }
	}
	
	public PlanningDelete() {}
	public PlanningDelete(Builder builder) {
		this.saveFor = builder.saveFor.length()==0 || builder.saveFor.indexOf("/")==0 ? builder.saveFor : ("/"+builder.saveFor);
		this.fileName = builder.fileName;
		this.realPath = builder.realPath;
	}
	public String getSaveFor() {
		return saveFor ;
	}
	public void setSaveFor(String saveFor) {
		this.saveFor = saveFor.length()==0 || saveFor.indexOf("/")==0 ? saveFor : ("/"+saveFor);
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRealPath() {
		return FolderCache.getUploadRootDir();
	}
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}
	
}

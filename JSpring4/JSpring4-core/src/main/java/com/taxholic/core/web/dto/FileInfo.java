package com.taxholic.core.web.dto;

public class FileInfo {
	
	private String filePath;
	private String fileSize;
	private String systemFilenm;
	private String userFilenm;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getSystemFilenm() {
		return systemFilenm;
	}
	public void setSystemFilenm(String systemFilenm) {
		this.systemFilenm = systemFilenm;
	}
	public String getUserFilenm() {
		return userFilenm;
	}
	public void setUserFilenm(String userFilenm) {
		this.userFilenm = userFilenm;
	}

}

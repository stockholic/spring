package kr.zchat.core.support.model;

public class FileInfo {
	
	private Integer boardFileSrl;
	private Integer boardSrl;
	private String filePath;
	private String fileSysNm;
	private String fileRealNm;
	private long fileSize;
	
	public Integer getBoardFileSrl() {
		return boardFileSrl;
	}
	public void setBoardFileSrl(Integer boardFileSrl) {
		this.boardFileSrl = boardFileSrl;
	}
	public Integer getBoardSrl() {
		return boardSrl;
	}
	public void setBoardSrl(Integer boardSrl) {
		this.boardSrl = boardSrl;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileSysNm() {
		return fileSysNm;
	}
	public void setFileSysNm(String fileSysNm) {
		this.fileSysNm = fileSysNm;
	}
	public String getFileRealNm() {
		return fileRealNm;
	}
	public void setFileRealNm(String fileRealNm) {
		this.fileRealNm = fileRealNm;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	
}

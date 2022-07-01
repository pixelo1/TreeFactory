package com.treefactory.board.vo;

public class BoardFileUploadVO {

	private Long uploadNo, boardNo;
	private String fileName, realSavePath, orgFileName;
	private Long fileSize;
	public Long getUploadNo() {
		return uploadNo;
	}
	public void setUploadNo(Long uploadNo) {
		this.uploadNo = uploadNo;
	}
	public Long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(Long boardNo) {
		this.boardNo = boardNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRealSavePath() {
		return realSavePath;
	}
	public void setRealSavePath(String realSavePath) {
		this.realSavePath = realSavePath;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "BoardFileUploadVO [uploadNo=" + uploadNo + ", boardNo=" + boardNo + ", fileName=" + fileName
				+ ", realSavePath=" + realSavePath + ", orgFileName=" + orgFileName + ", fileSize=" + fileSize + "]";
	}

	
	
}


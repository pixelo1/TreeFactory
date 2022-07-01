package com.treefactory.board.vo;

public class BoardFileUploadVO {

	private Long uploadNo, boardNo;
	private String fileName, realSavePath;
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
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	@Override
	public String toString() {
		return "BoardFileUploadVO [uploadNo=" + uploadNo + ", boardNo=" + boardNo + ", fileName=" + fileName
				+ ", realSavePath=" + realSavePath + ", fileSize=" + fileSize + "]";
	}
	
	
}


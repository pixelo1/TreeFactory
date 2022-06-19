package com.treefactory.reply.vo;

public class ReplyVO {

	private Long rno, no;
	private String content, id, name, writeDate;
	public Long getRno() {
		return rno;
	}
	public void setRno(Long rno) {
		this.rno = rno;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}
	@Override
	public String toString() {
		return "ReplyVO [rno=" + rno + ", no=" + no + ", content=" + content + ", id=" + id + ", name=" + name
				+ ", writeDate=" + writeDate + "]";
	}
	
	
}

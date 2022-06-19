package com.treefactory.util;

import com.webjjang.util.PageObject;

public class ReplyPageObject extends PageObject {

	//PageObject 가 가지고있는것 이외의 것을 더 선언해서 사용하면 된다
	private Long no;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	@Override
	public String toString() {
		return "ReplyPageObject ["+super.toString()+"no=" + no + "]";
	}
	
}

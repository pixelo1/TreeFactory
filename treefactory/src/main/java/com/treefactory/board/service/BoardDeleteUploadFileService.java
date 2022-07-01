package com.treefactory.board.service;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardFileUploadVO;
import com.treefactory.main.Service;

public class BoardDeleteUploadFileService implements Service{

	
	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	

	
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public Integer service(Object obj) throws Exception {
		System.out.println("BoardDeleteService-------------------");
		
		BoardFileUploadVO boardFileUploadVO = (BoardFileUploadVO)obj;
		// dao 클래스를 생성하고 메서드를 호출한다.
		return dao.deleteUploadFile(boardFileUploadVO);
	}
	
}

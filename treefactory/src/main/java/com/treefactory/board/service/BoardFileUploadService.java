package com.treefactory.board.service;

import java.util.List;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardFileUploadVO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Service;

public class BoardFileUploadService implements Service{

	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public Integer service(Object obj) throws Exception {
		System.out.println("BoardWriteService2-------------------");
		
		Object[] objs = (Object[]) obj;
		
		
		List<BoardFileUploadVO> listBoardFileUploadVO = (List<BoardFileUploadVO>) objs[0];
		Long no = (Long)objs[1];
		// dao 클래스를 생성하고 메서드를 호출한다.
		return dao.writeMultiUpload(listBoardFileUploadVO, no);
	}
	
}

package com.treefactory.board.service;

import java.util.List;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Service;

public class BoardWriteService2 implements Service{

	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public Integer service(Object obj) throws Exception {
		System.out.println("BoardWriteService2-------------------");
		
		List<BoardVO> vo = (List<BoardVO>) obj;
		// dao 클래스를 생성하고 메서드를 호출한다.
		return dao.writeMulti(vo);
	}
	
}

package com.treefactory.board.service;

import java.util.List;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Service;

public class BoardWriteService3 implements Service{

	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public BoardVO service(Object obj) throws Exception {
		System.out.println("BoardWriteService2-------------------");
		
		BoardVO vo = (BoardVO) obj;
		// dao 클래스를 생성하고 메서드를 호출한다.
		return dao.writeNoFile(vo);
		
//		return dao.getMaxNo(vo.getId());
	}
	
}

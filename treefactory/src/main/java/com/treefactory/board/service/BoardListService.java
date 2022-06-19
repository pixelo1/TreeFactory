package com.treefactory.board.service;

import java.util.List;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Service;
import com.treefactory.util.CategoryPageObject;

public class BoardListService implements Service {

	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	//페이지와 검색정보를 파라미터로 받아야한다
	public List<BoardVO> service(Object obj) throws Exception {
//		System.out.println("BoardListService-------------------");
		
		CategoryPageObject categoryPageObject= (CategoryPageObject) obj;
		
		// dao 클래스를 생성하고 메서드를 호출한다.
		//전체 데이터 개수를 구해서 pageObject에 넣어주는 처리가 되어야 한다
		categoryPageObject.setTotalRow(dao.getTotalRow(categoryPageObject));
		return dao.list(categoryPageObject);
	}

	
}

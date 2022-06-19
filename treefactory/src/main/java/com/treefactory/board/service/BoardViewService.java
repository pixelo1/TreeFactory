package com.treefactory.board.service;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Service;

public class BoardViewService implements Service {

	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public BoardVO service(Object obj) throws Exception {
		System.out.println("BoardViewService-------------------");
		
		//2개가 넘어오면 알아서 배열에 저장시킴
		Object[] objs = (Object[]) obj;
		
		long no = (long) objs[0];
		int inc = (int) objs[1];
		// dao 클래스를 생성하고 메서드를 호출한다.
		// 조회수 1증가 - inc가 1이면 1증가한다.
		if(inc == 1)
			dao.increase(no);
		// 데이터 가져오는 메서드 호출 가져온 데이터를 리턴한다.
		return dao.view(no);
	}
	
}

package com.treefactory.board.service;

import java.util.List;

import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.vo.BoardFileUploadVO;
import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Service;

public class BoardViewUploadFileService implements Service {

	private BoardDAO dao;
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public List<BoardFileUploadVO> service(Object obj) throws Exception {
		System.out.println("BoardViewService-------------------");
		
		//2개가 넘어오면 알아서 배열에 저장시킴
		long no = (long) obj;
		
		// dao 클래스를 생성하고 메서드를 호출한다.
		// 조회수 1증가 - inc가 1이면 1증가한다.
		// 데이터 가져오는 메서드 호출 가져온 데이터를 리턴한다.
		return dao.viewUploadFile(no);
	}
	
}

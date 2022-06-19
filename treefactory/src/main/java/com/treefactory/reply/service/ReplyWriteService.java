package com.treefactory.reply.service;

import com.treefactory.main.Service;
import com.treefactory.reply.dao.ReplyDAO;
import com.treefactory.reply.vo.ReplyVO;

public class ReplyWriteService implements Service{

	private ReplyDAO dao;
	public void setDao(ReplyDAO dao) {
		this.dao = dao;
	}
	@Override
	// Controller - [Service] - DAO(Data Access Object)
	public Integer service(Object obj) throws Exception {
		
		ReplyVO vo = (ReplyVO) obj;
		// dao 클래스를 생성하고 메서드를 호출한다.
		return dao.write(vo);
	}
	
}

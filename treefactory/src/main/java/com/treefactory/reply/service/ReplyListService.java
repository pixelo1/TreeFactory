package com.treefactory.reply.service;

import com.treefactory.main.Service;
import com.treefactory.reply.dao.ReplyDAO;
import com.treefactory.util.ReplyPageObject;

public class ReplyListService implements Service {

	private ReplyDAO dao;
	public void setDao(ReplyDAO dao) {
		this.dao = dao;
	}
	
	
	@Override
	public Object service(Object obj) throws Exception {
		// TODO Auto-generated method stub
		ReplyPageObject replyPageObject = (ReplyPageObject) obj;
		
		replyPageObject.setTotalRow(dao.getTotalRow(replyPageObject));
		
		return dao.list(replyPageObject);
	}

}

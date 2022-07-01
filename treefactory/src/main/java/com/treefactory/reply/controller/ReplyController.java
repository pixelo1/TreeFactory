package com.treefactory.reply.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.treefactory.board.vo.BoardVO;
import com.treefactory.main.Controller;
import com.treefactory.main.Execute;
import com.treefactory.reply.service.ReplyDeleteService;
import com.treefactory.reply.service.ReplyUpdateService;
import com.treefactory.reply.service.ReplyWriteService;
import com.treefactory.reply.vo.ReplyVO;
import com.treefactory.util.ReplyPageObject;
import com.webjjang.util.PageObject;

public class ReplyController implements Controller {

	//서버가 시작이 될 때 객체가 초기화 되어야만 한다 - DispacherServlet.init() 처리
	private ReplyWriteService replyWriteService;
	private ReplyUpdateService replyUpdateService;
	private ReplyDeleteService replyDeleteService;
	
	//댓글
	
	
	public void setReplyWriteService(ReplyWriteService replyWriteService) {
		this.replyWriteService = replyWriteService;
	}
	
	public void setReplyUpdateService(ReplyUpdateService replyUpdateService) {
		this.replyUpdateService = replyUpdateService;
	}

	public void setReplyDeleteService(ReplyDeleteService replyDeleteService) {
		this.replyDeleteService = replyDeleteService;
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception{
		
		System.out.println("ReplyController.execute()- 게싶판 처리하고 있다");
		
		String jsp = null;
		
		//uri - /Reply/실행서비스.do  - 처리 service 결정하는 - /list.do
		String uri = request.getServletPath();
		
		//두번째 슬레시부터 글자 찾기 - switch 문으로 해당되는것 실행되게끔 가져오는 처리
		String serviceUri = uri.substring(uri.indexOf("/", 1));
		System.out.println("ReplyController.execute().serviceUri - "+serviceUri);
		
		switch (serviceUri) {
		//일반게시판 리스트
			//일반게시판 글쓰기 폼
			//일반 게시판 글쓰기 처리
		case "/write.do":
			
			String strNo = request.getParameter("no");
			Long no = Long.parseLong(strNo);


			String content = request.getParameter("content");
			//session에서 login 꺼내면 loginVO가 나옴 로그인할때 저장을 그렇게해둠
			String id = "test";


			PageObject pageObject = PageObject.getInstance(request);
			//jsp(Controller) - BoardWriteService - BoardDAO.write(vo)
			//BoardVO 미리 생성해둠 파라미터값으로 넘겨주려고
			ReplyVO vo = new ReplyVO();
			vo.setNo(no);
			vo.setContent(content);
			vo.setId(id);

			//writeService 선택 , vo저장된 데이터 넘긴다
				// DB 등록 - ReplyWriteService - ReplyDAO
			Execute.service(replyWriteService, vo);

				jsp = "redirect:/board/view.do?no="+vo.getNo()+"&inc=0"
				+"&page="+pageObject.getPage()
				+"&perPageNum="+pageObject.getPerPageNum()
				+"&key="+pageObject.getKey()
				+"&word="+pageObject.getWord();
			break;

			
			case "/update.do":
				System.out.println("업데이트 컨트롤");
				String strRno = request.getParameter("rno");
				Long rno = Long.parseLong(strRno);

				content = request.getParameter("content");
				//session에서 login 꺼내면 loginVO가 나옴 로그인할때 저장을 그렇게해둠


				//jsp(Controller) - ReplyUpdateservice - ReplyDAO.update(vo)
				//BoardVO 미리 생성해둠 파라미터값으로 넘겨주려고
				vo = new ReplyVO();
				vo.setRno(rno);
				vo.setContent(content);

				//writeService 선택 , vo저장된 데이터 넘긴다
					// DB 등록 - ReplyWriteService - ReplyDAO
				Execute.service(replyUpdateService, vo);
				
				
				
				jsp = "redirect:/board/view.do?no="+request.getParameter("no")+"&inc=0"
						+"&page="+request.getParameter("page")
						+"&perPageNum="+request.getParameter("perPageNum")
						+"&key="+request.getParameter("key")
						+"&word="+request.getParameter("word")
						+"&replyPage="+request.getParameter("replyPage")
					;
				
				break;
				
			case "/delete.do":
				strRno = request.getParameter("rno");
				rno = Long.parseLong(strRno);

				id = "test";
				//session에서 login 꺼내면 loginVO가 나옴 로그인할때 저장을 그렇게해둠


				//jsp(Controller) - ReplyUpdateservice - ReplyDAO.update(vo)
				//BoardVO 미리 생성해둠 파라미터값으로 넘겨주려고
				vo = new ReplyVO();
				vo.setRno(rno);
				vo.setId(id);

				//writeService 선택 , vo저장된 데이터 넘긴다
					// DB 등록 - ReplyWriteService - ReplyDAO
				Execute.service(replyDeleteService, vo);

					jsp = "redirect:/board/view.do?no="+request.getParameter("no")+"&inc=0"
					+"&page="+request.getParameter("page")
					+"&perPageNum="+request.getParameter("perPageNum")
					+"&key="+request.getParameter("key")
					+"&word="+request.getParameter("word")
					+"&replyPage="+request.getParameter("replyPage") ;
				
				break;
			
		default:
			throw new Exception("잘못된 페이지를 요청하셨습니다");
		}
		


		
		return jsp;
	}

}

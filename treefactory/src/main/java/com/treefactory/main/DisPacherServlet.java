package com.treefactory.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.RequestContext;

import com.treefactory.board.controller.BoardController;
import com.treefactory.board.dao.BoardDAO;
import com.treefactory.board.service.BoardDeleteService;
import com.treefactory.board.service.BoardDeleteUploadFileService;
import com.treefactory.board.service.BoardFileUploadService;
import com.treefactory.board.service.BoardListService;
import com.treefactory.board.service.BoardUpdateService;
import com.treefactory.board.service.BoardViewService;
import com.treefactory.board.service.BoardViewUploadFileService;
import com.treefactory.board.service.BoardWriteService;
import com.treefactory.board.service.BoardWriteService2;
import com.treefactory.board.service.BoardWriteService3;
import com.treefactory.reply.controller.ReplyController;
import com.treefactory.reply.dao.ReplyDAO;
import com.treefactory.reply.service.ReplyDeleteService;
import com.treefactory.reply.service.ReplyListService;
import com.treefactory.reply.service.ReplyUpdateService;
import com.treefactory.reply.service.ReplyWriteService;

/**
 * Servlet implementation class DispatcherServlet
 * 
 * Spring 프레임워크를 만드는 사람이 @WebServlet 작성할 수 밖에 없다 (기본 구조를 설계하는사람이 한다)
 * 그래서 web.xml을 사용하여 개발자가 마음대로 정할 수 있도록 한다.
 */
//URL 매핑
//@WebServlet("*.do")
public class DisPacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//<> 내용안쓰면 앞의 내용과 같은 내용으로 쓴다는뜻
	private Map<String, Controller> controllerMap = new HashMap<>();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisPacherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		
		//객체 생성과 DI 적용
		System.out.println("DispacherServlet.init() - 서버가 시작될 때 실행되도록한다. 객체 생성과 DI(조립)을 해준다");
		
		
		//게시판에 실행할 service 객체 생성 저장 
		//서버 실행될때 생성시켜 저장시킨다
		// propertise 파일로 uri = 클래스이름 형식으로 짜두고 for문 으로 작성할수있음
		
		//객체 생성과 DI 적용이 잘 안되어 있으면 NullPointException이 발생된다
		BoardDAO boardDAO = new BoardDAO();
		
		BoardListService boardListService = new BoardListService();
		boardListService.setDao(boardDAO);
		
		BoardViewService boardViewService = new BoardViewService();
		boardViewService.setDao(boardDAO);
		
		BoardWriteService boardWriteService = new BoardWriteService();
		boardWriteService.setDao(boardDAO);
		
		BoardUpdateService boardUpdateService = new BoardUpdateService();
		boardUpdateService.setDao(boardDAO);
		
		BoardDeleteService boardDeleteService = new BoardDeleteService();
		boardDeleteService.setDao(boardDAO);
		
		BoardWriteService2 boardWriteService2 = new BoardWriteService2();
		boardWriteService2.setDao(boardDAO);
		
		BoardWriteService3 boardWriteService3 = new BoardWriteService3();
		boardWriteService3.setDao(boardDAO);
		
		BoardFileUploadService boardFileUploadService = new BoardFileUploadService();
		boardFileUploadService.setDao(boardDAO);
		
		BoardViewUploadFileService boardViewUploadFileService = new BoardViewUploadFileService();
		boardViewUploadFileService.setDao(boardDAO);
		
		BoardDeleteUploadFileService boardDeleteUploadFileService = new BoardDeleteUploadFileService();
		boardDeleteUploadFileService.setDao(boardDAO);
		
		BoardController boardController = new BoardController();

		boardController.setBoardListService(boardListService);
		boardController.setBoardViewService(boardViewService);
		boardController.setBoardWriteService(boardWriteService);
		boardController.setBoardUpdateService(boardUpdateService);
		boardController.setBoardDeleteService(boardDeleteService);
		boardController.setBoardWriteService2(boardWriteService2);
		boardController.setBoardWriteService3(boardWriteService3);
		boardController.setBoardFileUploadService(boardFileUploadService);
		boardController.setBoardViewUploadFileService(boardViewUploadFileService);
		boardController.setBoardDeleteUploadFileService(boardDeleteUploadFileService);
		//url 매핑은 맨아래에

		
		
		
		//댓글 객체 생성과 저장
		ReplyDAO replyDAO = new ReplyDAO();
		
		
		ReplyListService replyListService = new ReplyListService();
		replyListService.setDao(replyDAO);
		ReplyWriteService replyWriteService = new ReplyWriteService();
		replyWriteService.setDao(replyDAO);
		ReplyUpdateService replyUpdateService = new ReplyUpdateService();
		replyUpdateService.setDao(replyDAO);
		ReplyDeleteService replyDeleteService = new ReplyDeleteService();
		replyDeleteService.setDao(replyDAO);
		
		ReplyController replyController = new ReplyController();
		
		
		replyController.setReplyWriteService(replyWriteService);
		replyController.setReplyUpdateService(replyUpdateService);
		replyController.setReplyDeleteService(replyDeleteService);

		
		boardController.setReplyListService(replyListService);
		
		//URL 매핑은 맨아래에 한번에 보이게하자~
		//controllerMap.put("URI 맨앞에 모듈명", 실행할 컨트롤러)
		controllerMap.put("/board", boardController);
		controllerMap.put("/reply", replyController);
		
		
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	//들어오는 service가 무엇인지 구분하는 메서드
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//.do를 치면 service로 간다
		System.out.println("DispacherServlet.service() - *.do URL 요청하고 있다");
		
		//url 앞에 붙어 있는 모듈 이름으로 Controller를 찾아가게 한다.(=메서드를 호출한다)
		//request를 전달해서 처리된 결과 저장, 데이터 수집 진행
		// /board를 찾아내서 있으면 BoardController로 보낸다
		// /board/list.do 일때 두번째 슬레시 까지 잘라서 사용, board부분이 인덱스로 0인지 -1인지 확인후 사용(-1이면 board가 아니라는뜻)
		
		String uri = request.getServletPath();
		System.out.println("DispacherServlet.service() - "+ uri);
		
		//web.xml welcomepage세팅 을 index.do 로 설정함
		//localhost 또는 localhost/index.do 라고 주소를 입력하면 바로 /main/main.do 로 이동 
		if(uri.equals("/index.do")) {
			
			response.sendRedirect("/main/main.do");
			return;
		}
		
		//2번째 슬레시 의 위치를 잡아보자 2번째 인덱스부터 찾아보자
		//슬레시는 무조건 2개씩 들어온다 
		int pos = uri.indexOf("/", 1);
		//url 패턴 사용시 - /모듈명/처리.do 작성한다
		if(pos < 0 ) throw new ServletException("잘못된 페이지 요청");
		
		//모듈명 가져오기 0~첫번째 슬레쉬까지가 모듈명이다 /board <-/board/list
		String module = uri.substring(0, pos);
		System.out.println("DispacherServlet.service() - "+ module);
		
		try {
			//맨 앞의 /board가 있는가, 없으면 나중에는 예외처리로 넘겨야함
			
			Controller controller = controllerMap.get(module);
			if(controller == null) System.out.println("DispacherServlet.service() - 요청하신 모듈의 페이지가 존재하지 않는다");
			
			else {
				//board컨트롤러 실행 - jsp로 직접 가거나, redirect: 이 붙어있으면 url 로 이동한다
				String jsp = controller.execute(request);
				//jsp 에 데이터가 있으면
				if(jsp != null) {
					//redirect 가 넘어오지 않으면 jsp 로 직접 간다
					if(jsp.indexOf("redirect:") != 0) {
						//.getRequestDispatcher(이동 장소).forward(request, response)   - forward 이동메서드
						//강제로 jsp 에 접근시킴 - html...이 만들어진다
						//컨트롤러에서 가져온 데이터를 request에 데이터를 담아둔것을 가지고 response로 향한다 보여주기만 하는 데이터로 프론트단에서 사용한다
						request.getRequestDispatcher("/WEB-INF/views/"+jsp+".jsp").forward(request, response);
					}else {
						//9번째부터 끝까지 찾기
						//jsp = "redirect:/list.do~~~" url 로 이동
						//substring 전체중 일부를 가져온다는 메서드사용해서 찾기
						response.sendRedirect(jsp.substring("redirect:".length()));
					}
				}
			}
				
			
			//uri의 앞의 모듈 이름을 받아서 Controller 이동 시키는 조건문/ board부분이 인덱스로 0인지 -1인지 확인후 사용(-1이면 board가 아니라는뜻)
				//맨앞에 /board가 있는가를 확인
			//
//				if(uri.indexOf("/board") == 0) 
//					controllerMap.get("/board").execute(request);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				//오류난 e 를 그대로 넣어서 던져야 짜놓은 exception 사용가능하다
				throw new ServletException(e);
			}
		
	}

}

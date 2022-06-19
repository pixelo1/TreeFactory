<%@page import="com.treefactory.reply.vo.ReplyVO"%>
<%@page import="com.webjjang.member.vo.LoginVO"%>
<%@page import="java.util.function.LongToIntFunction"%>
<%@page import="com.treefactory.main.Execute"%>
<%@page import="com.treefactory.board.service.BoardWriteService"%>
<%@page import="com.treefactory.board.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
    
<%
//자바입니다. -- 스크립틀립 -> 순수한 자바 코드 입력시사용
//콘솔에 뜸
System.out.println("/board/replyWrite.jsp - 일반게시판댓글등록");
//DB에 공지 등록 처리 - BoardWriteservice - BoardDAO  기존 프로젝트에 썻던걸 사용


//주소창에 넘어오는 데이터받기
String strNo = request.getParameter("no");
Long no = Long.parseLong(strNo);


String content = request.getParameter("content");
//session에서 login 꺼내면 loginVO가 나옴 로그인할때 저장을 그렇게해둠
String id = ((LoginVO)session.getAttribute("login")).getId();

String strPerPageNum = request.getParameter("perPageNum");

//jsp(Controller) - BoardWriteService - BoardDAO.write(vo)
//BoardVO 미리 생성해둠 파라미터값으로 넘겨주려고
ReplyVO vo = new ReplyVO();
vo.setNo(no);
vo.setContent(content);
vo.setId(id);

//writeService 선택 , vo저장된 데이터 넘긴다
	// DB 등록 - ReplyWriteService - ReplyDAO
Execute.service(request.getServletPath(), vo);

	response.sendRedirect("view.jsp?no="+vo.getNo()+"&inc=0"
	+"&page="+request.getParameter("page")
	+"&perPageNum="+request.getParameter("perPageNum")
	+"&key="+request.getParameter("key")
	+"&word="+request.getParameter("word")
	
			);

%>
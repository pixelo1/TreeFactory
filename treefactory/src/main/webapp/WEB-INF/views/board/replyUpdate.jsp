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
System.out.println("/board/replyUpdate.jsp - 일반게시판댓글수정");
//DB에 공지 등록 처리 - ReplyUpdateservice - ReplyDAO  기존 프로젝트에 썻던걸 사용

//글번호는 url 에서 직접 받아서 넘기는것으로 한다
//주소창에 넘어오는 데이터받기
String strRno = request.getParameter("rno");
Long rno = Long.parseLong(strRno);

String content = request.getParameter("content");
//session에서 login 꺼내면 loginVO가 나옴 로그인할때 저장을 그렇게해둠


//jsp(Controller) - ReplyUpdateservice - ReplyDAO.update(vo)
//BoardVO 미리 생성해둠 파라미터값으로 넘겨주려고
ReplyVO vo = new ReplyVO();
vo.setRno(rno);
vo.setContent(content);

//writeService 선택 , vo저장된 데이터 넘긴다
	// DB 등록 - ReplyWriteService - ReplyDAO
Execute.service(request.getServletPath(), vo);

	response.sendRedirect("view.jsp?no="+request.getParameter("no")+"&inc=0"
	+"&page="+request.getParameter("page")
	+"&perPageNum="+request.getParameter("perPageNum")
	+"&key="+request.getParameter("key")
	+"&word="+request.getParameter("word")
	+"&replyPage="+request.getParameter("replyPage")
	
			);

%>
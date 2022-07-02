<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@taglib prefix="pageNav" tagdir="/WEB-INF/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>일반게시판보기</title>


<style type="text/css">
</style>

</head>
<body>

	<div class="container">
		<h2>일반게시판 보기</h2>
		<table class="table">
			<tr>
				<th>번호</th>
				<td>${vo.no}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${vo.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${vo.content}</td>
			</tr>
			<tr>
				<th>이미지</th>
				<td>
				<c:forEach items="${listBoardFileUploadVO }" var="fileVO">
				<img alt="" src="${fileVO.fileName }"><p>${fileVO.orgFileName }</p> 
				</c:forEach>
				</td>				
			</tr>
			<tr>
				<th>작성자</th>
				<td>${vo.id}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td>${vo.writeDate}</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${vo.hit}</td>
			</tr>
			<tr>
				<!-- 수정, 삭제, 리스트 버튼 : 2칸을 1칸으로 사용 colspan 옆에칸과 합침 / 위에칸와 합치는건 rowspan-->
				<td colspan="2" style="border: none">
				<a
					href="updateForm.do?no=${vo.no}&inc=0&page=${pageObject.page }&perPageNum=${pageObject.perPageNum }&key=${pageObject.key }&word=${pageObject.word }&category=${category}"
					class="btn btn-default">수정</a> 
					
					<a
					href="delete.do?no=${vo.no}&perPageNum=${pageObject.perPageNum }"
					class="btn btn-danger" onclick="return confirm('정말, 삭제하시겠습니까?')">삭제</a>
					<a
					href="list.do?page=${pageObject.page }&perPageNum=${pageObject.perPageNum }&period=${pageObject.period }&key=${pageObject.key }&word=${pageObject.word }&category=${category}"
					class="btn btn-info">리스트</a></td>
			</tr>
		</table>
<!-- 댓글 시작 -->
<%@include file="../board/replyList.jsp" %>

	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹짱 일반게시판 수정</title>


<script type="text/javascript">

</script>
<style type="text/css">
/*
th,td{
	border: 1px solid #444;
	padding: 5px;
}
th{
	background: #000;
	color: #fff;
}
*/

</style>


<script type="text/javascript">
function sendData() {
// 	alert("submit!");
	//필수 입력 항목 체크
	if(!checkEmpty("title", "제목"))return false;
	if(!checkEmpty("content", "내용"))return false;
	if(!checkEmpty("writer", "작성자"))return false;
	
	//입력 데이터의 패턴 체크 - 정규표현식
	//제목의 정규 표현식 - 글자 갯수가 3자이상 100자 이내로 작성
	//   /^\w{3,100}/   w 한글 불가
	if(!checkReg("title","제목",reg_title,reg_title_msg)) return false;
	if(!checkReg("content","내용",reg_content,reg_content_msg)) return false;
	if(!checkReg("writer","작성자",reg_name_kr,reg_name_kr_msg)) return false;
	
	
}
$(function(){
	//아직 진행중
	$("#deleteFileBtn").click(function(){
		if(confirm("파일전부삭제")){
			var inc = "0";
			var no = "${vo.no}";
			location = "deleteAllFile.do?no="+no+"&inc="+inc+"&category=${param.category }";
			
		};
	})
});
</script>
</head>
<body>


<div class="container">
<h2>일반게시판 수정</h2>
<!-- method- 데이터를 넘기는 방식 - get:주소뒤에,post:눈에 안보이게 전달 -->
<!--  action을 생략가능 - 생략시 같은주소를 호출한다 현재 writeForm을 호출 -->
<form action="update.do" method="post"  enctype="multipart/form-data" id="updateForm">
<input name="page" value="${pageObject.page }" type="hidden">
<input name="perPageNum" value="${pageObject.perPageNum }" type="hidden">
<input name="key" value="${pageObject.key }" type="hidden">
<input name="word" value="${pageObject.word }" type="hidden">
<input name="category" value="${param.category }" type="hidden">
<input type="hidden" name="deleteCheck" id="deleteCheck">
	<div class="form-group">
    <label for="no">번호</label>
    <input type="text" class="form-control" id="no" name="no" value="${vo.no}" readonly="readonly">
  </div>
	<div class="form-group">
    <label for="title">제목</label>
    <input type="text" class="form-control" id="title" name="title" value="${vo.title}">
  </div>
	<div class="form-group">
    <label for="content">내용</label>
    <textarea rows="7" class="form-control" name="content" id="content">${vo.content}</textarea>
  </div>
	<div class="form-group">
    <label >이미지 표시</label>
	    <c:forEach items="${listBoardFileUploadVO }" var="listFile" varStatus="vs">
	    	<img alt="" src="${listFile.fileName }"><p>${listFile.orgFileName }</p>
	    	<input name="del" value="${listFile.fileName}" type="hidden">
	    </c:forEach>
  	</div>
	<div class="form-group">
    <label for="image">이미지수정</label>
    <input type="file" class="form-control" id="images" name="images" multiple="multiple">
  </div>
	<div class="form-group">
    <label for="writer">작성자</label>
     <input type="text" class="form-control" id="writer" name="id" readonly="readonly" value="${vo.id}">
  </div>
		<!-- submit : 데이터 전달 , reset : 처음상태, button: 동작이없다(js로 동작만들어서 사용가능) -->
			<button type="submit" class="btn btn-default">등록</button>
			<button type="reset" class="btn btn-default">새로고침</button>
			<button type="button" onclick="history.back()" class="btn btn-default">취소</button>
</form>
			<button type="button" class="btn btn-default" id="deleteFileBtn">파일삭제</button>
</div>
</body>
</html>
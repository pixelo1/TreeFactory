<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹짱 일반게시판 등록</title>


<style type="text/css">
/*
th,td {
 dotted 점선으로나옴
	border: 1px solid #444;
	padding: 5px
}

th{
background: #000;
color: #fff;
}
*/
</style>
<!-- 외부 js 파일 불러오기 : script tag 사이에 있는 코드는 무시 당한다 -->
<script type="text/javascript" src="/js/regEx.js">
</script>
<script type="text/javascript" src="/js/form.js">
</script>

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
</script>
</head>
<body>
<div class="container">
	<h2>일반 게시판 등록</h2>
	<!-- method- 데이터를 넘기는 방식 - get:주소뒤에,post:눈에 안보이게 전달 -->
	
	<!--  action을 생략가능 - 생략시 같은주소를 호출한다 현재 writeForm을 호출 -->
	<form action="write.do" method="post" enctype="multipart/form-data">
<input name="perPageNum" value=${param.perPageNum } type="hidden">
	
	<div class="form-group">
	    <label for="title">제목</label>
	    <input type="text" class="form-control" id="title" name="title" maxlength="100">
	  </div>
	  <div class="form-group">
	    <label for="content">내용</label>
	    <textarea rows="7" placeholder="내용 입력" name="content" id="content" class="form-control"></textarea>
	    
	  </div>
	  <div class="form-group">
	    <label for="image">이미지</label>
	    <input type="file" class="form-control" id="image" name="image">
	  </div>
	<div class="form-group">
	    <label for="writer">작성자</label>
	    <input type="text" class="form-control" id="writer" name="writer" maxlength="100">
	  </div>
			<!-- submit : 데이터 전달 , reset : 처음상태, button: 동작이없다(js로 동작만들어서 사용가능) -->
			<button type="submit" onclick="return sendData();" class="btn btn-default">등록</button>
			<button type="reset" class="btn btn-info">새로고침</button>
			<button type="button" onclick="history.back()" class="btn btn-danger">취소</button>
	
	</form>
	
</div>
</body>
</html>
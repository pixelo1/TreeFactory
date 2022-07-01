<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib prefix="categoryPageNav" tagdir="/WEB-INF/tags" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 일반게시판 리스트</title>


<style type="text/css">

.dataRow, button{
}
.dataRow:hover{
/* hover 는 마우스가 올라갔을때만 변한다 */
cursor: pointer;
	background: #eee
}

</style>
<script type="text/javascript">
//body 태그 부분이 처리가 끝나면 실행되는 형식
//함수 또는 메서드 는 동일하다 js => function 이름(){~~~} => 실행 : 이름(); 호출
// jquery 는 이름이 없다  
// function(){~~} -> 익명함수 : 콜백함수 선언 -> body처리가 끝나고 function$(func) 의 func 가 변수의 이름이된다
	$(function(){
		//tag -> tag[] 선택
		//.class -> class[] 선택
		//#id -> id 1개 선택
		$(".dataRow").click(function(){
			//글번호 수집
			var no = $(this).find(".no").text();
			
// 			alert("dataRow 클릭 : "+no);
			location="view.do?no="+no+"&inc=1&page=${categoryPageObject.page}&perPageNum=${categoryPageObject.perPageNum}"
					+"&key=${categoryPageObject.key}&word=${categoryPageObject.word}&category=${categoryPageObject.category}";
					
			
		})
		
	})
</script>

</head>
<body>

<div class="container">
<h2>일반게시판 리스트</h2>

<div>
	<div class="btn-group " id="categoryBtnDIV">
		<a href="list.do?page=1&perPageNum=${categoryPageObject.perPageNum }&category=all" class="btn btn-default">all</a>
		<a href="list.do?page=1&perPageNum=${categoryPageObject.perPageNum }&category=정기모임/스터디" class="btn btn-default">정기모임/스터디</a>
		<a href="list.do?page=1&perPageNum=${categoryPageObject.perPageNum }&category=포럼" class="btn btn-default">포럼</a>
	</div>


<!-- 검색창 action에 안쓰면 지금페이지로 온다-->
<form action="list.do" method="get" class="form-inline">
	<!-- key 지정을 name으로 함 perPageNum에 값을 넣는다 -->
	<input name="perPageNum" value="${categoryPageObject.perPageNum }" type="hidden">
	<input name="category" value="${categoryPageObject.category }" type="hidden">
	<div class="form-group">
	<!-- input,select, -->
	<select name="key" class="form-control">
		<option value="t" ${(categoryPageObject.key == "t")? "selected": "" }>제목</option>
		<option value="c" ${(categoryPageObject.key == "c")? "selected": "" }>내용</option>
		<option value="i" ${(categoryPageObject.key == "i")? "selected": "" }>아이디</option>
		<option value="tc" ${(categoryPageObject.key == "tc")? "selected": "" }>제목/내용</option>
		<option value="ti" ${(categoryPageObject.key == "ti")? "selected": "" }>제목/작성자</option>
		<option value="ci" ${(categoryPageObject.key == "ci")? "selected": "" }>내용/작성자</option>
		<option value="tci" ${(categoryPageObject.key == "tci")? "selected": "" }>모두</option>
	</select>
	</div>
  <div class="input-group">
  <!-- key 로 word가 넘어ㅏ게 name 지정 -->
    <input type="text" class="form-control" placeholder="Search" name="word" value="${categoryPageObject.word }">
    <div class="input-group-btn">
      <button class="btn btn-default" type="submit">
        <i class="glyphicon glyphicon-search"></i>
      </button>
    </div>
  </div>
</form>
</div>

<!-- 리스트 갯수 정해져있음 -->

<table class="table">

	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>아이디</th>
		<th>작성일</th>
		<th>조회수</th>
		<th>추천수</th>
		<th>카테고리</th>
	</tr>
	<!--  데이터 있는 만큼 반복문 처리 : 데이터 한개 당 tr 한개 -->
	<!--  items 데이터 여러개일때쓴다 var 변수명 설정 (기본값은 pagecontext 에서 생긴다) vo.getNo()라고 쓸수도 있음 알아서 get해온다-->
	<c:forEach items="${list}" var="vo">
	<tr class="dataRow">
		<td class="no">${vo.no }</td>
		<td>${vo.title }</td>
		<td>${vo.id }</td>
		<td>${vo.writeDate }</td>
		<td>${vo.hit }</td>
		<td>${vo.rec }</td>
		<td>${vo.category }</td>
	</tr>
	</c:forEach>
	
	<!-- 위에가 다 5개 칸으로 나뉘어져있어서 한줄에 5개 하나로 묶음 -->
	<tr>
		<td colspan="5" style="border: none;">
		<!-- request.setAttribute("categoryPageObject", categoryPageObject); 를 꺼내쓴다 -->
		<div class="row">
			<div class="col-md-8">
			<categoryPageNav:categoryPageNav categoryPageObject="${categoryPageObject }" listURI="list.do" query="&category=${categoryPageObject.category }"/>
			</div>
			<div class="col-md-4 text-right">
			<a href="writeForm.do?perPageNum=${categoryPageObject.perPageNum }" id="writebtn" class="btn btn-default">글쓰기</a>
			</div>
		</div>
		</td>
	</tr>

</table>

</div>
</body>
</html>
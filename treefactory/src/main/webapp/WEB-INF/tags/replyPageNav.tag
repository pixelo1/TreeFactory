<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<%@ attribute name="pageObject" required="true"
 type="com.webjjang.util.PageObject" %>

<%@ attribute name="replyPageObject" required="true"
 type="com.treefactory.util.ReplyPageObject" %>

<%@ attribute name="listURI" required="true"
 type="java.lang.String" %>
<%@ attribute name="query" required="false"
 type="java.lang.String" %>



<% /** PageNation을 위한 사용자 JSP 태그  *
	 * 작성자 웹짱 이영환 강사 
	 * 작성일 2022.05.27
	 * 버전 4.0
	 
	 * 사용방법 :<pageObject:pageNav listURI="호출 List URL"
	 			pageObject= "웹짱 페이지 객체" query="댓글 페이지 등 뒤에 붙이는 쿼리" />
   */ %>

<% request.setAttribute("noLinkColor", "#999"); %>
<% request.setAttribute("tooltip", " data-toggle=\"tooltip\" data-placement=\"top\" "); %>
<% request.setAttribute("noMove", " title=\"no move page!\" "); %>

<ul class="pagination">
  	<li data-page=1>
		<c:if test="${replyPageObject.page > 1 }">
		<!-- 맨 첫페이지로 이동 2페이지이거나 12페이지 등등 일때 |<<표시 버튼 -->
	  		<a href="${listURI }?no=${replyPageObject.no }&inc=0&page=${pageObject.page }&perPageNum=${pageObject.perPageNum}&key=${pageObject.key }&word=${pageObject.word }&replyPage=1${query}"
	  		  title="click to move first page!" ${tooltip } >
	  			<i class="glyphicon glyphicon-fast-backward"></i>
	  		</a>
  		</c:if>
		<c:if test="${replyPageObject.page == 1 }">
	  		<a href="" onclick="return false"
	  		 ${noMove }  ${tooltip } >
	  			<i class="glyphicon glyphicon-fast-backward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</c:if>
	</li>
	
	
	<!-- 이전페이지 활성화 |< 1개 페이지 전으로 이동(10묶음 을 넘긴다)-->
	<!-- |< 이전 페이지선택  , data-page 는 태그안에 데이터를 숨긴다-->
	<li data-page=${replyPageObject.startPage -1 }>
		<c:if test="${replyPageObject.startPage > 1 }">
	  		<a href="${listURI }?no=${replyPageObject.no }&inc=0&page=${pageObject.page }&perPageNum=${pageObject.perPageNum}&key=${pageObject.key }&word=${pageObject.word }&replyPage=${replyPageObject.page-1}${query}"
	  		  title="click to move previous page group!" ${tooltip } >
	  			<i class="glyphicon glyphicon-step-backward"></i>
	  		</a>
	  	</c:if>
	  	<!-- 첫 페이지면 nomove 움직일수없다 -->
		<c:if test="${replyPageObject.startPage == 1 }">
	  		<a href="" onclick="return false"
	  		 ${noMove } ${tooltip }>
	  			<i class="glyphicon glyphicon-step-backward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</c:if>
  	</li>
  	
  	<!-- 11,12,13,14,15,16,17,18,19,20  처럼 10개씩 나오는게 cnt -->
  	<!-- active 는 뭐지?-->
	<c:forEach begin="${replyPageObject.startPage }" end="${replyPageObject.endPage }" var="cnt">
  	<li ${(replyPageObject.page == cnt)?"class=\"active\"":"" } 
  	 data-page=${cnt }>
  	 	<!-- 페이지와 cnt가 같으면 링크가 없음  댓글 페이지가 cnt 와 같다면 클릭할수없다, 1개 페이징 에서 1~10 까지의 숫자로 나뉜 페이지 = cnt -->
  	 	<c:if test="${replyPageObject.page == cnt }">
  			<a href="" onclick="return false"
  			 ${noMove } ${tooltip }>${cnt}</a>
  	 	</c:if>
  	 	<!-- 페이지와 cnt가 같지 않으면 링크가 있음 cnt로 가야함-->
  	 	<c:if test="${replyPageObject.page != cnt }">
  			<a href="${listURI }?no=${replyPageObject.no }&inc=0&page=${pageObject.page }&perPageNum=${pageObject.perPageNum}&key=${pageObject.key }&word=${pageObject.word }&replyPage=${cnt}${query}"
	  		 title="click to move ${cnt } page" ${tooltip }>${cnt}</a>
  		</c:if>
  	</li>
	</c:forEach>
	
	<!-- 마지막페이지의 다음페이지 10까지오면 11페이지로 넘어가게 >|  (10묶음 을 넘긴다)-->
	<c:if test="${replyPageObject.endPage < replyPageObject.totalPage }">
	  	<li data-page=${replyPageObject.endPage + 1 }>
	  		<a href="${listURI }?no=${replyPageObject.no }&inc=0&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}&key=${pageObject.key }&word=${pageObject.word }&replyPage=${replyPageObject.endPage + 1}${query}"
	  		  title="click to move next page group!" ${tooltip } >
	  			<i class="glyphicon glyphicon-step-forward"></i>
	  		</a>
	  	</li>
  	</c:if>
	<c:if test="${replyPageObject.endPage == replyPageObject.totalPage }">
	  	<li data-page=${replyPageObject.endPage + 1 }>
	  		<a href="" onclick="return false"
	  		 ${noMove }  ${tooltip } >
	  			<i class="glyphicon glyphicon-step-forward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</li>
  	</c:if>
  	
  	<!-- 마지막 페이지 로 이동  >>| -->
	<c:if test="${replyPageObject.page < replyPageObject.totalPage }">
	  	<li data-page=${replyPageObject.totalPage }>
	  		<a href="${listURI }?no=${replyPageObject.no }&inc=0&page=${pageObject.page }&perPageNum=${pageObject.perPageNum}&key=${pageObject.key }&word=${pageObject.word }&replyPage=${replyPageObject.totalPage}${query}"
	  		  title="click to move last page!" ${tooltip } >
		  		<i class="glyphicon glyphicon-fast-forward"></i>
	  		</a>
	  	</li>
  	</c:if>
	<c:if test="${replyPageObject.page == replyPageObject.totalPage }">
	  	<li data-page=${replyPageObject.totalPage }>
	  		<a href="" onclick="return false"
	  		 ${noMove }  ${tooltip } >
		  		<i class="glyphicon glyphicon-fast-forward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</li>
  	</c:if>
</ul> 

<script>
$(document).ready(function(){
  $('[data-toggle="tooltip"]').tooltip();
  $(".pagination").mouseover(function(){
//   		$(".tooltip > *:last").css({"background-color": "red", "border": "1px dotted #444"});   
	});
});
</script>

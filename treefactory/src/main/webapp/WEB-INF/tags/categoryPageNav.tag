<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ attribute name="categoryPageObject" required="true"
 type="com.treefactory.util.CategoryPageObject" %>
<%@ attribute name="listURI" required="true"
 type="java.lang.String" %>
<%@ attribute name="query" required="false"
 type="java.lang.String" %>

<% /** PageNation을 위한 사용자 JSP 태그  *
	 * 작성자 웹짱 이영환 강사 
	 * 작성일 2022.05.27
	 * 버전 4.0
	 
	 * 사용방법 :<categoryPageObject:pageNav listURI="호출 List URL"
	 			categoryPageObject= "웹짱 페이지 객체" query="댓글 페이지 등 뒤에 붙이는 쿼리" />
   */ %>

<% request.setAttribute("noLinkColor", "#999"); %>
<% request.setAttribute("tooltip", " data-toggle=\"tooltip\" data-placement=\"top\" "); %>
<% request.setAttribute("noMove", " title=\"no move page!\" "); %>

<ul class="pagination">
  	<li data-page=1>
		<c:if test="${categoryPageObject.page > 1 }">
		<!-- 맨 첫페이지로 이동 |<<표시 버튼 -->
	  		<a href="${listURI }?page=1&perPageNum=${categoryPageObject.perPageNum}&key=${categoryPageObject.key }&word=${categoryPageObject.word }${query}"
	  		  title="click to move first page!" ${tooltip } >
	  			<i class="glyphicon glyphicon-fast-backward"></i>
	  		</a>
  		</c:if>
		<c:if test="${categoryPageObject.page == 1 }">
	  		<a href="" onclick="return false"
	  		 ${noMove }  ${tooltip } >
	  			<i class="glyphicon glyphicon-fast-backward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</c:if>
	</li>
	
	
	<!-- 이전페이지 활성화 <<, >> -->
	<li data-page=${categoryPageObject.startPage -1 }>
		<c:if test="${categoryPageObject.startPage > 1 }">
	  		<a href="${listURI }?page=${categoryPageObject.startPage - 1 }&perPageNum=${categoryPageObject.perPageNum}&key=${categoryPageObject.key }&word=${categoryPageObject.word }${query}"
	  		  title="click to move previous page group!" ${tooltip } >
	  			<i class="glyphicon glyphicon-step-backward"></i>
	  		</a>
	  	</c:if>
		<c:if test="${categoryPageObject.startPage == 1 }">
	  		<a href="" onclick="return false"
	  		 ${noMove } ${tooltip }>
	  			<i class="glyphicon glyphicon-step-backward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</c:if>
  	</li>
	<c:forEach begin="${categoryPageObject.startPage }" end="${categoryPageObject.endPage }" var="cnt">
  	<li ${(categoryPageObject.page == cnt)?"class=\"active\"":"" } 
  	 data-page=${cnt }>
  	 	<!-- 페이지와 cnt가 같으면 링크가 없음 -->
  	 	<c:if test="${categoryPageObject.page == cnt }">
  			<a href="" onclick="return false"
  			 ${noMove } ${tooltip }>${cnt}</a>
  	 	</c:if>
  	 	<!-- 페이지와 cnt가 같지 않으면 링크가 있음 -->
  	 	<c:if test="${categoryPageObject.page != cnt }">
  			<a href="${listURI }?page=${cnt }&perPageNum=${categoryPageObject.perPageNum}&key=${categoryPageObject.key }&word=${categoryPageObject.word }${query}"
	  		 title="click to move ${cnt } page" ${tooltip }>${cnt}</a>
  		</c:if>
  	</li>
	</c:forEach>
	<!-- 다음페이지 -->
	<c:if test="${categoryPageObject.endPage < categoryPageObject.totalPage }">
	  	<li data-page=${categoryPageObject.endPage + 1 }>
	  		<a href="${listURI }?page=${categoryPageObject.endPage + 1 }&perPageNum=${categoryPageObject.perPageNum}&key=${categoryPageObject.key }&word=${categoryPageObject.word }${query}"
	  		  title="click to move next page group!" ${tooltip } >
	  			<i class="glyphicon glyphicon-step-forward"></i>
	  		</a>
	  	</li>
  	</c:if>
	<c:if test="${categoryPageObject.endPage == categoryPageObject.totalPage }">
	  	<li data-page=${categoryPageObject.endPage + 1 }>
	  		<a href="" onclick="return false"
	  		 ${noMove }  ${tooltip } >
	  			<i class="glyphicon glyphicon-step-forward" style="color: ${noLinkColor};"></i>
	  		</a>
	  	</li>
  	</c:if>
  	<!-- 마지막 페이지 -->
	<c:if test="${categoryPageObject.page < categoryPageObject.totalPage }">
	  	<li data-page=${categoryPageObject.totalPage }>
	  		<a href="${listURI }?page=${categoryPageObject.totalPage }&perPageNum=${categoryPageObject.perPageNum}&key=${categoryPageObject.key }&word=${categoryPageObject.word }${query}"
	  		  title="click to move last page!" ${tooltip } >
		  		<i class="glyphicon glyphicon-fast-forward"></i>
	  		</a>
	  	</li>
  	</c:if>
	<c:if test="${categoryPageObject.page == categoryPageObject.totalPage }">
	  	<li data-page=${categoryPageObject.totalPage }>
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

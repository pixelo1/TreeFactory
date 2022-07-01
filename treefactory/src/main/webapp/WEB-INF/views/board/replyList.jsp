<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	$(function() {
		
		//자동으로 실행된는 부분 ---------
		
		//댓글 제목 디자인 적용
		$("#tabs").tabs();
		
		//등록버튼을 댓글 내용의 높이와 맞춰보자
		$("#replyWriteBtn").height($("#content").height());
		
		//댓글 수정과 취소 버튼 보이지 않게 처리(처음 자동으로 event 필요없이) / 아래댓글에서 수정 버튼 누르면 수정버튼이 나옴
		$("#replyUpdateBtn, #cancelReplyUpdateBtn").hide();
		
		//각각의 댓글에 있는 수정 이벤트
		$(".replyUpdateBtn").click(function(){
			//등록버튼 없애기, 수정과 취소 버튼 보이기
			$("#replyWriteBtn").hide();
			$("#replyUpdateBtn, #cancelReplyUpdateBtn").show();
			
			//클릭한 데이터의 rno 찾아오기, tag안에 속성으로 data-rno="10" 형식으로 들어가있다
			var rno = $(this).data("rno");
			$("#rno").val(rno);
			
			//댓글 내용 세팅 parent 대신 간단히  설정가능
			var content = $(this).closest("div").find(".content").text();
			$("#content").val(content);
			
		});
		
		// 각각의 댓글에 있는 삭제 버튼 이벤트발생시 처리내용
		$(".replyDeleteBtn").click(function(){
			//클릭한 데이터의 rno 찾아오기, tag안에 속성으로 data-rno="10" 형식으로 들어가있다
			var rno = $(this).data("rno");
			
			$("#rno").val(rno);
			//삭제는 rno 만 넘겨주고 가서 세션에서 아이디가져온다
			if(confirm("댓글을 정말 삭제하시겠습니까?")){
// 				location="replyDelete.do?rno="+rno;
			
			//리스트의 삭제 버튼을 누르면 댓글 쓰기 Form 버튼의 submit을 강제로 작동시켜 주소값으로 넘기는것을 알아서 넘기게해준다
			//원래 submit 실행되면 write,update쪽으로 가게 했었음
			//content 는 비어있는값으로 넘어가서 그냥 버린다
			$("#replyForm").attr("action", "/reply/delete.do");
			$("#replyForm").submit();
		
		}
		});		
		
		//댓글 등록 버튼 이벤트
		$("#replyWriteBtn").click(function(){
// 			alert("댓글 등록 클릭");

//			데이터 유효성 검사
//			맨 앞과 맨뒤의 공백문자 제거
			$("#content").val($("#content").val().trim())
			//공백문제 없는 데이터 받아오기
			var content = $("#content").val();
			if(!content) {
// 				alert("댓글을 입력해주세요");
				$("#content").focus();
				
				return false;
			}
			
			//폼 데이터 전송 URL 설정
			$("#replyForm").attr("action", "/reply/write.do");
			$("#replyForm").submit();
		
		});

		//댓글 수정 버튼 이벤트
		$("#replyUpdateBtn").click(function(){
// 			alert("수정 진행");
			
			//action 속성의 값을 변경
//			데이터 유효성 검사
//			맨 앞과 맨뒤의 공백문자 제거
			$("#content").val($("#content").val().trim())
			//공백문제 없는 데이터 받아오기
			var content = $("#content").val();
			if(!content) {
// 				alert("댓글을 입력해주세요");
				$("#content").focus();
				
				return false;
							
			}
			
			$("#replyForm").attr("action", "/reply/update.do");
			$("#replyForm").submit();
			
		})
		
		//댓글 취소 버튼 이벤트
		$("#cancelReplyUpdateBtn").click(function(){
			
			$("#replyWriteBtn").show();
			$("#replyUpdateBtn, #cancelReplyUpdateBtn").hide();
			
			//입력하고있는 데이터 지우기
			$("#content").val("");
		});
		
		
		
	});
</script>
<!-- 댓글 시작 -->
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">댓글</a></li>
			</ul>
<%-- 			<c:if test="${!empty login }"> --%>
				<!-- 댓글 등록, 수정을 위해서 사용되는 div,form -->
				<div style="padding: 15px">
				
				<!-- 댓글 작성 -->
					<form id="replyForm" method="post">
	
						<input type="hidden" name="no" value="${vo.no }">
						<!--  돌아올때 댓글 페이지가있어야 원래있던곳으로 넘어간다 -->
						<input type="hidden" name="replyPage" value="${replyPageObject.page }">
						<!-- 수정할때 필요한 정보입니다 - 수정을 누르면 rno 가 세팅되게 할예정 -->
						<input type="hidden" name="rno" id="rno">
						<input type="hidden" name="page" value="${pageObject.page }" >
						<input type="hidden" name="perPageNum" value="${pageObject.perPageNum }" >
						<input type="hidden" name="key" value="${pageObject.key }" >
						<input type="hidden" name="word" value="${pageObject.word }" >
	
						<div class="input-group">
							<textarea rows="3" name="content" class="form-control" style="resize: none;" id="content" style="resize"></textarea>
							<div class="input-group-btn">
								<button class="btn btn-default" type="button" style="height: 100%; font-size: 12pt;" id="replyWriteBtn">등록</button>
								<button class="btn btn-default" type="button" style="height: 100%; font-size: 12pt;" id="replyUpdateBtn">수정</button>
								<br>
								<button class="btn btn-default" type="button" style="height: 100%; font-size: 12pt;" id="cancelReplyUpdateBtn">취소</button>
							</div>
						</div>
					</form>
				</div>
<%-- 			</c:if> --%>
<!-- 댓글 리스트 -->
			<div id="tabs-1">
				<div class="list-group">

					<c:if test="${!empty list}">
						<!--  위에 vo 가 있으니  -->
						<c:forEach items="${list }" var="replyVo">
						<div class="list-group-item" >
							<!-- 데이터가 있는 만큼 반복처리 된다. -->
							<h5 class="list-group-item-heading">
								[${replyVo.name } (${replyVo.writeDate })] 
								
<%-- 								<c:if test="${!empty login && login.id == replyVo.id }"> --%>
									<span class="pull-right">
										<button class="replyUpdateBtn btn btn-default btn-xs" data-rno="${replyVo.rno }">수정</button>
										<button class="replyDeleteBtn btn btn-default btn-xs" data-rno="${replyVo.rno }">삭제</button>
									</span>
<%-- 								</c:if> --%>
							</h5>
							<p class="list-group-item-text content">${replyVo.content }</p>
						</div>
						</c:forEach>
					<div>
					<!-- 댓글의 페이지 정보는 query -->
						<pageNav:replyPageNav listURI="view.do" pageObject="${pageObject }" replyPageObject="${replyPageObject }"/>
					</div>
					
					</c:if>
					
					
					<c:if test="${empty list }">
						<div class="list-group-item">
							댓글 데이터가 존재하지 않습니다
						</div>
					</c:if>
				</div>
			</div>
		</div>


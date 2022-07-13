<!-- sitemesh 사용을 위한 설정 파일 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>treefactory:<decorator:title /></title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
	
  <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.js"></script>
	
<style type="text/css">
header, footer {
	background: AntiqueWhite;
}

pre {
	background: white;
	border: 0px;
}

/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

/* Add a gray background color and some padding to the footer */
footer {
	background-color: #eee;
	padding: 25px;
	border:  1px solid #ccc;
}

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
}

article {
	min-height: 400px;
	margin-top: 80px;
}

#welcome {
	color: grey;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
<decorator:head/>
</head>
<body>
	<header>
<!-- 		<div><img href="#"/></div> -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="/">Logo</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav">
<!-- 						<li><a href="/notice/list.do">공지사항</a></li> -->
						<li><a href="/board/list.do">게시판</a></li>
<!-- 						<li><a href="/image/list.do">갤러리</a></li> -->
						<li><a href="/qna/list.do">Q &amp; A</a></li>
						<c:if test="${!empty login }">
							<li><a href="/message/list.do">메시지</a></li>
							<c:if test="${login.gradeNo == 9 }">
								<li><a href="/member/list.do">회원관리</a></li>
							</c:if>
						</c:if>
					</ul>
					<ul class="nav navbar-nav navbar-right">
					<c:if test="${empty login }">
						<li><a href="/member/writeForm.do">회원가입</a></li>
						<li><a href="/member/loginForm.do">로그인</a></li>
					</c:if>
					<c:if test="${!empty login }">
						<li><a href="/member/view.do">내정보보기</a></li>
						<li><a href="/member/logout.do">로그아웃</a></li>
					</c:if>
					</ul>
				</div>
			</div>
		</nav>
	</header>
<!-- 내가 작성한 내용삽입 위치 -->
	<article>
		<decorator:body />
	</article>
	<div class="container">
	<footer class="container-fluid text-center">
		<p>treefactory</p>
	</footer>
	</div>
</body>
</html>
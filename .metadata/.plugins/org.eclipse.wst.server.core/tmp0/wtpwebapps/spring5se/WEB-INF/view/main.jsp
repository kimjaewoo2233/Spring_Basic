<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Main</title>
</head>
<body>

	<c:if test="${empty authinfo }">	
				<p>환영합니다</p>
				<p>
					<a href="<c:url value="/register/step1"/>">[회원가입하기]</a>
					<a href="<c:url value="/login"/>">[로그인]</a>
				</p>
	</c:if>
	<c:if test="${!empty authinfo }">
			<p>${authinfo.name }님 환영합니다</p>
			<p>
					<a href="<c:url value="/edit/changePassword"/>">[비밀번호 변경하러 가기]</a>
					<a href="<c:url value="/logout"/>">[로그아웃]</a>
			</p>
	
	</c:if>


</body>
</html>
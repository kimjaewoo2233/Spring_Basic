<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">

	<title><spring:message code="member.register"/></title>

</head>
<body>
	<h2><spring:message code="term"/></h2>
	<p>��� ����</p>
	<form action="step2" method="post">
	<label>
		<input type="checkbox" name="agree" value="true">�������
		<spring:message code="term.agree"/>
	</label>
	<input type="submit" value="<spring:message code="next.btn"/>"/>
	</form>
</body>
</html>
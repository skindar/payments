<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.fiedietsov.i18n.text" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="./CSS/style.css" rel="stylesheet">
</head>
<body>
	<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="logout">logout</a>
	</div>
	
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>		
	<br><br>
	<form method="post" action="addnew">
		<div id="add-form">
			
			<input name="account" type="text" placeholder="Номер счёта" pattern="\d{14}" title="Enter 14 digits!">
			<br> <select name="select"><option value="visa">VISA</option>
				<option value="master">MASTERCARD</option></select><br> <input
				name="exp" type="text" placeholder="01/19" pattern="\d{2}\/\d{2}" title='Enter date format "01/19"!'><br> <input
				name="last" type="text" placeholder="***" pattern="\d{3}" title='Enter last 3 digits'> <br>
			<button type="submit" value="reg">Добавить</button>
		</div>

	</form>

</body>
</html>
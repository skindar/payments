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
<title>Депозит</title>
<link href="./CSS/style.css" rel="stylesheet">
</head>
<body>
	<c:set var="noCards" value="у вас нет добавленных карт" />
	<c:set var="incomeSuccessText" value="ваш платеж осуществлён успешно" />
<%-- 		<c:if test="${not empty sessionScope.incomeSuccess }">
			<c:remove var="incomeSuccess"/>
		</c:if> --%>
		<c:if test="${not empty sessionScope.paySuccess }">
			<c:remove var="paySuccess"/>
		</c:if>
	<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="logout">logout</a>
	</div>
	
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>	
	<br><br>			
	<div>
		<form action="../pays/putdeposit" method="POST">
			<select name="selectAcc">
				<c:if test="${empty sessionScope.accounts}">
					<option>
						<p>${noCards}
						<p>
					</option>
				</c:if>
	
	
				<c:forEach var="item" items="${sessionScope.accounts}">
					<option>${item.account}</option>
				</c:forEach>
	
			</select> <br>
			<fmt:message key="deposit.label.buttonCredit" var="buttonCredit" />
			<input type="text" name="addMoney" pattern="(\d?)+\.?\d+" title="Only digits!"> <input type="submit" value="${buttonCredit}">
		</form>
	</div>

	<c:if test="${sessionScope.incomeSuccess eq true}">
	
		<font color="green"> ${incomeSuccessText}</font>
	</c:if>
</body>
</html>
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
	<c:if test="${not empty sessionScope.incomeSuccess }">
			<c:remove var="incomeSuccess"/>
		</c:if>
		<c:if test="${not empty sessionScope.paySuccess }">
			<c:remove var="paySuccess"/>
		</c:if>
	<c:set var="noCards" value="у вас нет добавленных карт" />
		<fmt:message key="cart.label.prepNoPay" var="prepNoPay" />
		<c:set var="confPayResultText" value="Вы успешно совершили платёж" />
	<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="logout">logout</a>
	</div>
	
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>		
	<br>
	<br>
	<c:if test="${empty sessionScope.payments}">
	<fmt:message key="cart.label.prepNoPay" var="prepNoPay" />
		${prepNoPay}
	</c:if>
	
	<c:if test="${not empty sessionScope.payments}">
	<fmt:message key="cart.label.buttonAccept" var="buttonAccept" />
	<fmt:message key="cart.label.buttonDelete" var="buttonDelete" />
		<table class="spc" border="0" cellspacing="2" cellpadding="2" width="68%">
			<c:forEach var="item" items="${sessionScope.payments}">
				<tr><td>${item.dtime}</td><td>${item.account}</td>
				<td>${item.summary}</td><td>${item.total}</td>
				<td>${item.balance}</td>
				<td><button name="confirm" value="${item.dtime}" form="forma">${buttonAccept}</button></td>
				<td><button name="delete" value="${item.dtime}" form="forma">${buttonDelete}</button></td>
				
				</tr>
			</c:forEach>
	
		</table>		
	</c:if>
	<form action="../pays/confpay" method="POST" id="forma">
	
	</form>
	<c:if test="${sessionScope.confPayResult eq true}">
			<font color="green"> ${confPayResultText}</font>
	</c:if>
		<div class="footer">	
	    <form>
	      <select id="language" name="language" onchange="submit()">
	          <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
	          <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
	      </select>
	    </form>	
	</div>	
	

</body>
</html>
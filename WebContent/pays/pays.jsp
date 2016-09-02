<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.fiedietsov.i18n.text" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Перевод</title>
<link href="./CSS/style.css" rel="stylesheet">
</head>
<body>
	<c:set var="noCards" value="у вас нет добавленных карт" />
	<c:set var="paySuccess" value="ваш перевод проведен успешно" />
	
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
		<form action="../pays/pays" method="POST">
		<c:if test="${empty sessionScope.accounts}">
			<select>
				<option>
					<p>${noCards}
					<p>
				</option>
			</select>
		</c:if>
		<fmt:message key="mypays.button.payFrom" var="payFrom" />	
		<fmt:message key="mypays.button.payTo" var="payTo" />	
		<fmt:message key="mypays.button.summa" var="summa" />
		
			
			<c:if test="${not empty sessionScope.accounts}">
			<div class="main">
			<div class="field">		 <label for="n">${payFrom}: </label> <select name="selectAcc1" id="n">
						<c:forEach var="item" items="${sessionScope.accounts}">
							<option>${item.account}</option>
						</c:forEach>
			
					</select>
			</div>
			<div class="field">
				<label for="n2">${payTo}: </label> <select name="selectAcc2" id="n2">
						<c:forEach var="item" items="${sessionScope.accounts}" >
							<option>${item.account}</option>
						</c:forEach>
				</select> 
			</div>
				
		
		<fmt:message key="mypays.button.paySubmit" var="ButtonPaySubmit" />	
		<fmt:message key="mypays.button.payAttention" var="payAttention" />
			<div class="field">
			<label for="n3">${summa}: </label><input type="text" name="pays" pattern="(\d?)+\.?\d+" id="n3" required > <br>
			</div></div> <br><br><br><br>
			${payAttention}
			<br><br><input type="submit" value="${ButtonPaySubmit}" /> <br><br>
		</c:if>	
		
		
		</form>
			<c:if test="${sessionScope.paySuccess eq 'successfully'}">
				<p >
					<fmt:message key="payments.pay.successMoneySend" var="successMoneySend" />
					<font color="green">${successMoneySend}</font>
				</p>
			</c:if>
			<c:if test="${sessionScope.paySuccess eq 'negative'}">
				<p >
					<fmt:message key="payments.pay.noMoney" var="cardNoMoney" />
					<font color="red">${cardNoMoney}</font>
				</p>
			</c:if>
	</div>
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
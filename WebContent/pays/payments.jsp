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
<title>Payments</title>
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
	<c:set var="successOp" value="Ваш платеж был добавлен в корзину!" />
	<c:set var="unSuccessOp" value="Платеж не был добавлен, проверьте баланс" />
	<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="logout">logout</a>
	</div>
	
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>
	<br>
	<fmt:message key="payments.pay.labelPay" var="textLabelPay" />	
	<h3>${textLabelPay}</h3> 

	
	<form action="../pays/prepay" method="post">
		<c:if test="${empty sessionScope.accounts}">
					<select>
					<option>
						<p>${noCards}
						<p>
					</option>
					</select>
		</c:if>
		<c:if test="${not empty sessionScope.accounts}">
			<fmt:message key="payments.pay.fromPay" var="textFromPay" />	
			 ${textFromPay}: <select name="selectAcc1">
				<c:forEach var="item" items="${sessionScope.accounts}">
					<option>${item.account}</option>
				</c:forEach>
			</select> <br>
			<fmt:message key="payments.pay.toPay" var="textToPay" />
			${textToPay}:<input  required type="text" name="toPay" pattern="\d{5,14}" title="Only digits! min 5, max 14"><br>
			<fmt:message key="payments.pay.purpose" var="textPurpose" />
			${textPurpose}:<input required type="text" name="toPayInfo" pattern="[A-Za-zА-Яа-яЁё\s0-9]{3,50}" title="Only symbols and digits max 50"><br>
		
			<fmt:message key="payments.pay.summa" var="textSumma" />
			${textSumma}: <input required type="text" name="money" pattern="(\d?)+\.?\d+" title="Only digits!"> <input type="submit" value="Подтвердить"><br>
		</c:if>	
			
		</form>
		<c:if test="${sessionScope.resultOp == 1}">
				<p >
					<fmt:message key="payments.pay.goodAddToCart" var="goodAddToCart" />
					<font color="green">${goodAddToCart}</font>
				</p>
		</c:if>
		<c:if test="${sessionScope.resultOp == 11}">
				<p >
					<fmt:message key="payments.pay.badAddToCart" var="badAddToCart" />
					<font color="red">${badAddToCart}</font>
				</p>
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
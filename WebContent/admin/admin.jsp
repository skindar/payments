<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.fiedietsov.i18n.text" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Admin area</title>
		<link href="../pays/CSS/style.css" rel="stylesheet">
		<script type="text/javascript" charset="utf-8" async="" src="../pays/CSS/script.js"></script>
	</head>
<body>
<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="../pays/logout">logout</a>
	</div>
<c:if test="${sessionScope.isAdmin == true }">
	
		<input type="button" class="menu" value="администрирование"
			onclick="location.href='/payments/admin/admin.jsp'"
			text-align="right" />
			
	</c:if>
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>
<h3>Информация по аккаунту: <c:if test="${not empty sessionScope.userEmail}">
<c:if test="${sessionScope.customerStatus eq true}">
	<font color="red">${sessionScope.userEmail}</font>
</c:if>
<c:if test="${sessionScope.customerStatus eq false}">
	<font color="green">${sessionScope.userEmail}</font>
</c:if>
</c:if> <button name="locked" value="${sessionScope.userEmail}" form="forma">заблокировать</button></h3> 
<c:set var="noCards" value="нет добавленных карт"/>

<form action="../pays/getaccounts" method="post" id="forma">
	<input type="text" placeholder="login" name="userEmail">
	<input type="submit" title="отправить" >
</form>
<form action="../pays/gettransactions" method="post">
	<select name="selectAcc">
		<c:if test="${empty sessionScope.accounts}">
			<option >
   			<p>${noCards}<p>
   			</option>
		</c:if>
		<c:forEach var="item" items="${sessionScope.accounts}">
			<option value="${item.account}">
				${item.account}
			</option>
		</c:forEach>
	</select>
	<input type="submit" value="Получить данные">
</form><br>


<table class="spc" border="0" cellspacing="2" cellpadding="2" width="68%">
<tr><td>card</td><td>additions</td> <td>balance</td><td>currency</td><td>verified</td><td>switch</td></tr>

	<c:forEach var="item" items="${sessionScope.accounts}">
		<tr><td>${item.account}</td><td>${item.info}</td> 
		<td>${item.acc_balance}</td><td>${item.currency}</td>
		<td>${item.verified}</td>
		<td><button name="verified" value="${item.account}" form="forma">verify</button></td>	
		</tr>


	</c:forEach>

</table>
<c:if test="${not empty sessionScope.trancs }">
		<br>
		<div id="render_me">
		<table class="spc" id="history" border="0" cellspacing="2" cellpadding="2" width="98%">
			<tr>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Data</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Account</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Summary</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Summa</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Balance</td>
	          </tr>
			
			<c:forEach var="item" items="${sessionScope.trancs}">
				
				<tr><td>${item.dtime}</td><td>${item.account}</td>
				<td>${item.summary}</td><td>${item.total}</td>
				<td>${item.balance}</td>
				
				</tr>
		
		
			</c:forEach>
		
			</table>	
		</div>
	</c:if>

</body>
</html>
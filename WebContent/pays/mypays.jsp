<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/jstl/isLocked.tld" prefix="xx"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.fiedietsov.i18n.text" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<link href="./CSS/style.css" rel="stylesheet">
			<link href="./CSS/component.css" rel="stylesheet">
			<script type="text/javascript" charset="utf-8" async="" src="./CSS/script.js"></script>
		<title>User page</title>
	
	</head>
<body>
		<c:if test="${not empty sessionScope.incomeSuccess }">
			<c:remove var="incomeSuccess"/>
		</c:if>
		<c:if test="${not empty sessionScope.paySuccess }">
			<c:remove var="paySuccess"/>
		</c:if>
		<c:if test="${sessionScope.confPayResult eq true}">
			<c:remove var="confPayResult" scope="session"/>
		</c:if>
		<c:if test="${not empty sessionScope.resultOp }">
			<c:remove var="resultOp"/>
		</c:if>
	<c:if test="${sessionScope.customerStatus eq true}">
		<xx:isLocked value="true"/>
	</c:if>
	

	<fmt:message key="login.label.logged" var="textLogged" />	
	<div align="right">
		${textLogged}: ${pageContext.request.userPrincipal.name}
		<a href="logout">logout</a>
	</div>
	
	
	<!-- admin button -->
	<c:if test="${sessionScope.isAdmin == true }">
	
		<input type="button" class="menu" value="администрирование"
			onclick="location.href='/payments/admin/admin.jsp'"
			text-align="right" />
			
	</c:if>

	
		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>
		
		
		<c:if test="${empty sessionScope.accounts}">
			<fmt:message key="login.label.noCards" var="noCards" />	
			<p>${noCards}<p>
		</c:if>
		<c:if test="${not empty sessionScope.accounts}">	
			<div class="div-table">
			<br>
			<table class="spc" border="0" cellspacing="2" cellpadding="2" width="68%">

	
			<tr>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Card</td>
	            <td class="thd cur" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Balance</td>
	            <td class="thd" onclick="sort(this)" title="Нажмите на заголовок, чтобы отсортировать колонку">Summary</td>
	
	       </tr>
			<c:forEach var="item" items="${sessionScope.accounts}">
				<tr><td>${item.account}</td> 
				<td>${item.acc_balance}</td>
				<td>${item.currency}</td>
				</tr>
		
		
			</c:forEach>
		
			</table>
			</div>	
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
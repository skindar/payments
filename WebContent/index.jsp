<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="ua.nure.fiedietsov.i18n.text" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="${language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payments/unauth/CSS/style.css" rel="stylesheet">
<title>Система платежений "Easy payments"</title>
</head>
<body>
	<h4>
		<fmt:message key="login.button.label" /><br>
		<fmt:message key="login.button.label2" />
	</h4>
	<br>
	<form method="post" action="j_security_check" class="login">
	<div class="main">
		<div class="login-field">
			<label for="login"><fmt:message key="login.label.username" />:</label> <input type="email"
				name="j_username" id="login" placeholder="name@example.com">
		</div>

		<div class="login-field">
			<label for="password"><fmt:message key="login.label.password" />:</label> <input type="password"
				name="j_password" id="password" placeholder="********">
		</div>
	</div><br><br>
		<fmt:message key="login.button.submit" var="buttonValue" />
		<fmt:message key="login.button.reg" var="buttonRegValue" />
		<p class="login-submit">
			<button type="submit" value="login" class="login-button">${buttonValue}</button>      
				<input type="button" value="${buttonRegValue}"
				onclick="location.href='./unauth/regis'" />
		</p>
	</form>
			<p class="forgot-password">
			<a href="/"><fmt:message key="login.button.forget" /></a>
		</p>

	

    <form>
      <select id="language" name="language" onchange="submit()">
          <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
          <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
      </select>
    </form>
</body>
</html>
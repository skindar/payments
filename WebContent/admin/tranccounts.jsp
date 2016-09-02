<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="../pays/CSS/style.css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" async="" src="../pays/CSS/script.js"></script>
</head>
<body>
<form action="/payments/admin/getcounts" method="GET">
<input type="submit">
</form>
	<c:if test="${not empty sessionScope.trncCount}">
		<table class="spc" border="0" cellspacing="2" cellpadding="2" width="68%">
		<tr ><td class="thd cur" onclick="sort(this)" title="sort">Name</td><td >Count</td></tr>
		<c:forEach var="item" items="${sessionScope.trncCount}">
		
		
			<tr><td>${item.email}</td><td>${item.countTranc}</td> 
			</tr>
		
		</c:forEach>
		</table>
	</c:if>

</body>
</html>
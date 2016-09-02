<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Регистрация</title>

</head>
<body>
	<h3>Регистрация нового пользователя</h3>
	<form  method="post" action="regis">
				<div id="reg-form">
				<input name="name" type="text" placeholder="ФИО" pattern="[A-Za-zА-Яа-яЁё\s0-9]{10,50}" title="Only symbols and digits max 50"> <br>
				<input name="email" type="text" placeholder="e-mail"><br> 
				<input name="pass" type="password" placeholder="password" pattern="[A-Za-zА-Я0-9]{6,14}" title="Only symbols and digits max 50"> <br>
				<input name="repass" type="password" placeholder="password" pattern="[A-Za-zА-Я0-9]{6,14}" title="retype password"><br>
				<button type="submit" value="reg" >Регистрация</button>
				</div>

	</form>

</body>
</html>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
	<HEAD>		
    	<meta charset="UTF-8">
    </HEAD>
	<body>
		<h1>Please Log Into Your Account</h1>
		<p>
			Please use the form below to log into your account.
		</p>
		<form action="../group-buying-web/j_spring_security_check" method="post">
			<label for="j_username">Login</label>:
			<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
			<br />
		
			<input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>
			<label for="_spring_security_remember_me">Remember Me?</label>
			<br />
			<label for="j_password">Password</label>:
			<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
			<br />
		
			<input type="submit" value="Login"/>
		</form>
	</body>
</html>
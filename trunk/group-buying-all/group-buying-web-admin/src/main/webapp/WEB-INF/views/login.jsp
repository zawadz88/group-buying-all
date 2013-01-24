<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <html>
   <body>
		<h1>Please Log Into Your Account</h1>
		<p>
			Please use the form below to log into your account.
		</p>
		<form action="../group-buying-web-admin/j_spring_security_check" method="post">
			<label for="j_username">Login</label>:
			<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
			<br />
		
			<label for="j_password">Password</label>:
			<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
			<br />
		
			<input type="submit" value="Login"/>
		</form>
	</body>
</html>

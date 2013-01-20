<%@ include file="./includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
		<ul>
			<jsp:include page="./common/menu.jsp"></jsp:include>
			<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
				<li>
					<a href="<c:url value="/logout" />" >Wyloguj</a>
				</li>
			</authz:authorize>
		</ul>
	<h1>Dodaj nowego klienta</h1>
	<form:form modelAttribute="client">
		<table border="1">
			<tr>
				<td><spring:message code="client.username.label" /></td>
				<td>
					<form:input path="username" />
					<form:errors path="username"/>
				</td>
			</tr>
	
			<tr>
				<td><spring:message code="client.password.label" /></td>
				<td>
					<form:password path="password" />
					<form:errors path="password" />
				</td>
			</tr>
	
			<tr>
				<td><spring:message code="client.firstName.label" /></td>
				<td>
					<form:input path="firstName" />
					<form:errors path="firstName" />
				</td>
			</tr>
	
			<tr>
				<td><spring:message code="client.lastName.label" /></td>
				<td>
					<form:input path="lastName" />
					<form:errors path="lastName" />
				</td>
			</tr>
	
			<tr>
				<td><spring:message code="client.email.label" /></td>
				<td>
					<form:input path="email" />
					<form:errors path="email" />
				</td>
			</tr>
		</table>
		<hr />
		<table border="1">
			<tr>
				<td>
					<input type="submit" name="_eventId_submit" value="<spring:message code="client.submit.label" />" colspan="2" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
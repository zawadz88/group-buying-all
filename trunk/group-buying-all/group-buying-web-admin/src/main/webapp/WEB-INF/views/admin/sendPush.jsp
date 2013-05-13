<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
		
    	<meta charset="UTF-8">
</head>
<body>
		<ul>
			<jsp:include page="../common/adminMenu.jsp"></jsp:include>
			
			<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
				<li>
					<a href="<c:url value="/logout" />" >Wyloguj</a>
				</li>
			</authz:authorize>
		</ul>
	<h1>Wyślij wiadomość Push</h1>
	<form:form modelAttribute="pushNotification">
		<h3><form:errors /></h3>
		<table border="1">
			<tr>
				<td><spring:message code="push.offerId.label" /></td>
				<td>
					<form:input path="offerId" />
					<form:errors path="offerId"/>
				</td>
			</tr>
			<tr>
				<td><spring:message code="push.message.label" /></td>
				<td>
					<form:input path="message" />
					<form:errors path="message"/>
				</td>
			</tr>
	
		</table>
		<hr />
		<table border="1">
			<tr>
				<td>
					<input type="submit" value="<spring:message code="client.submit.label" />" colspan="2" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
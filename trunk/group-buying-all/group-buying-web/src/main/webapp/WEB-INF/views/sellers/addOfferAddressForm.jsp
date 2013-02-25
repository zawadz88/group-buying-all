<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>		
    	<meta charset="UTF-8">
</head>
<body>
		<ul>
			<jsp:include page="../common/sellersMenu.jsp"></jsp:include>
			<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
				<li>
					<a href="<c:url value="/logout" />" >Wyloguj</a>
				</li>
			</authz:authorize>

		</ul>
	<h1>Dodaj nową ofertę</h1>
	<form:form modelAttribute="offer">
		<table border="1">
			<tr>
				<td><spring:message code="offer.address.street.label" /></td>
				<td>
					<form:input path="address.street" />
					<form:errors path="address.street"/>
				</td>
			</tr>
	
			<tr>
				<td><spring:message code="offer.address.city.label" /></td>
				<td>
					<form:input path="address.city" />
					<form:errors path="address.city" />
				</td>
			</tr>
			<tr>
				<td><spring:message code="offer.address.postalCode.label" /></td>
				<td>
					<form:input path="address.postalCode" />
					<form:errors path="address.postalCode" />
				</td>
			</tr>
		</table>
		<hr />
		<table border="1">
			<tr>
				<td>
					<input type="submit" name="_eventId_back" value="<spring:message code="offer.back.label" />" colspan="2" />
				</td>
				<td>
					<input type="submit" name="_eventId_submit" value="<spring:message code="offer.submit.label" />" colspan="2" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
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
	<h1>Dodaj nowego sprzedawcę</h1>
	<form:form modelAttribute="seller">
		<table border="1">
			<tr>
				<td><spring:message code="seller.phoneNumber.label" /></td>
				<td>
					<form:input path="phoneNumber" />
					<form:errors path="phoneNumber" />
				</td>
			</tr>
			<tr>
				<td><spring:message code="seller.address.street.label" /></td>
				<td>
					<form:input path="address.street" />
					<form:errors path="address.street" />
				</td>
			</tr>
			<tr>
				<td><spring:message code="seller.address.postalCode.label" /></td>
				<td>
					<form:input path="address.postalCode" />
					<form:errors path="address.postalCode" />
				</td>
			</tr>
			<tr>
				<td><spring:message code="seller.address.city.label" /></td>
				<td>
					<form:input path="address.city" />
					<form:errors path="address.city" />
				</td>
			</tr>
		</table>
		<hr />
		<table border="1">
			<tr>
				<td>
					<input type="submit" name="_eventId_back" value="<spring:message code="seller.back.label" />" colspan="2" />
				</td>
				<td>
					<input type="submit" name="_eventId_submit" value="<spring:message code="seller.submit.label" />" colspan="2" />
				</td>
			</tr>
		</table>
	</form:form>
</body>
</html>
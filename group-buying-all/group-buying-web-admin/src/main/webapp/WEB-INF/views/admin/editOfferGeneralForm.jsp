<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>		
		<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
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
		<h1>edytuj ofertę</h1>
		<form:form modelAttribute="offer">
			<table border="1">
				<tr>
					<td><spring:message code="offer.title.label" /></td>
					<td>
						<form:input path="title" />
						<form:errors path="title"/>
					</td>
				</tr>
		
				<tr>
					<td><spring:message code="offer.lead.label" /></td>
					<td>
						<form:input path="lead" />
						<form:errors path="lead"/>
					</td>
				</tr>
				
				<tr>
					<td><spring:message code="offer.description.label" /></td>
					<td>
						<form:input path="description" />
						<form:errors path="description" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.conditions.label" /></td>
					<td>
						<form:input path="conditions" />
						<form:errors path="conditions" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.imageUrl.label" /></td>
					<td>
						<form:input path="imageUrl" />
						<form:errors path="imageUrl" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.category.label" /></td>
					<td>
						<form:select path="category.categoryId">
							<form:option value="0" label="Select a category" />
							<form:options items="${categories}" itemLabel="name" itemValue="categoryId"/>
						</form:select>
						<form:errors path="category.categoryId" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.startDate.label" /></td>
					<td>
						<form:input path="startDate" />
						<form:errors path="startDate" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.endDate.label" /></td>
					<td>
						<form:input path="endDate" />
						<form:errors path="endDate" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.price.label" /></td>
					<td>
						<form:input path="price" />
						<form:errors path="price" />
					</td>
				</tr>
				<tr>
					<td><spring:message code="offer.priceBeforeDiscount.label" /></td>
					<td>
						<form:input path="priceBeforeDiscount" />
						<form:errors path="priceBeforeDiscount" />
					</td>
				</tr>
				
				<tr>
					<td><spring:message code="offer.state.label" /></td>
					<td>
						<form:select path="state" items="${stateList}">
						</form:select>
						<form:errors path="state" />
					</td>
				</tr>
			</table>
			<hr />
			<table border="1">
				<tr>
					<td>
						<input type="submit" name="_eventId_submit" value="<spring:message code="offer.submit.label" />" colspan="2" />
					</td>
				</tr>
			</table>
		</form:form>
	</body>
</html>
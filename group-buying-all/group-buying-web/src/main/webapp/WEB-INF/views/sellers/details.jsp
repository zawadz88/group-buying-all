<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${not empty offer}">
	
		<html>
			<head>
				<title>For sellers</title>
			</head>
			<body>
				<h1>Welcome <authz:authentication property="principal.username"/></h1>
				<h2>Waiting offers</h2>
				<ul>
					<jsp:include page="../common/sellersMenu.jsp"></jsp:include>
					<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
						<li>
							<a href="<c:url value="/logout" />" >Wyloguj</a>
						</li>
					</authz:authorize>
				</ul>
				<div id="offerDetails">
					<table border="1">
						<tr>
							<td><spring:message code="offer.title.label" /></td>
							<td>
								<c:out value="${offer.title}"></c:out>
							</td>
						</tr>
						<tr>
							<td><spring:message code="offer.description.label" /></td>
							<td>
								<c:out value="${offer.description}"></c:out>
							</td>
						</tr>

					</table>
				</div>
			</body>
		</html>
	</c:when>
	<c:otherwise>
		Wystąpił błąd
	</c:otherwise>
</c:choose>
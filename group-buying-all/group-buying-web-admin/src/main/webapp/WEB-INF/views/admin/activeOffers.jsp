<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>For sellers</title>
	</head>
	<body>
		<h1>Welcome <authz:authentication property="principal.username"/></h1>
		<h2>Active offers</h2>
		<ul>
			<jsp:include page="../common/adminMenu.jsp"></jsp:include>
			
			<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
				<li>
					<a href="<c:url value="/logout" />" >Wyloguj</a>
				</li>
			</authz:authorize>
		</ul>
		<div id="offerList">
			<table>
				<tr>
					<td>
						Title
					</td>
					<td>
						Username
					</td>
					<td>
						Start date
					</td>
					<td>
						End date
					</td>
					<td>
					</td>
					<td>
					</td>
				</tr>
				<c:forEach items="${offers}" var="offer">
					<tr>
						<td>
							<c:out value="${offer.title}"></c:out>
						</td>
						<td>
							<c:out value="${offer.seller.email}"></c:out>
						</td>
						<td>
							<c:out value="${offer.startDate}"></c:out>
						</td>
						<td>
							<c:out value="${offer.endDate}"></c:out>
						</td>
						<td>
							<a href="<c:url value="details.do?offerId=${offer.offerId}"/>">Details</a>
						</td>
						<td>
							<a href="<c:url value="editOffer.do?offerId=${offer.offerId}"/>">Edit</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>
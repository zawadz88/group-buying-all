<%@ include file="./includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>		
    	<meta charset="UTF-8">
</head>
<body>
	<h1>Konto sprzedawcy założone</h1>
		<ul>
			<jsp:include page="./common/menu.jsp"></jsp:include>
			<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
				<li>
					<a href="<c:url value="/logout" />" >Wyloguj</a>
				</li>
			</authz:authorize>
		</ul>
</body>
</html>
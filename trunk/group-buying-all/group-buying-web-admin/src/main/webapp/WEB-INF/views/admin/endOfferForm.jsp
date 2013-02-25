<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>		
<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>
	</head>
	<body>
		<h1>Oferta zmieniona.</h1>
				<ul>
				<jsp:include page="../common/adminMenu.jsp"></jsp:include>
				<authz:authorize ifAnyGranted="ROLE_USER,ROLE_ADMIN">
					<li>
						<a href="<c:url value="/logout" />" >Wyloguj</a>
					</li>
				</authz:authorize>
				
			</ul>
	</body>
</html>
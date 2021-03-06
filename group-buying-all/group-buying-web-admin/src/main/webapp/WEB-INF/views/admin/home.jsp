<%-- <?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="common/header.jsp">
	<jsp:param name="pageTitle" value="Home"/>
</jsp:include>

<h1>Witamy w systemie zakupów grupowych!</h1>
<p>
	We have many great breeds of pet available for your perusal.
</p>
<ul>
	<li><a href="listPets.do?species=dog">Dogs</a></li>
	<li><a href="listPets.do?species=cat">Cats</a></li>
</ul>

<jsp:include page="common/footer.jsp"/> --%>

<%@ include file="../includes/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title>Strona startowa - admin</title>
	</head>
	<body>
		<%-- <h1>Strona główna - Witaj <authz:authentication property="principal.username"/></h1> --%>
	
		<ul>
			<jsp:include page="../common/adminMenu.jsp"></jsp:include>
			<authz:authorize ifAnyGranted="ROLE_ADMIN">
				<li>
					<a href="<c:url value="/logout" />" >Wyloguj</a>
				</li>
			</authz:authorize>
		</ul>
	</body>
</html>
<%@ page contentType="text/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
{
	success: false,
	errors:[
	<c:if test="${exception != null}">
	{id: 'message', msg: '${exception.message}'},
	{id: 'cause', msg: '${exception.cause}'}
	</c:if>
	<c:forEach items="${errors}" var="error">
	{id:'${error.field}', msg: '<spring:message code="${error.code}"/>'}
	</c:forEach>
	]
}
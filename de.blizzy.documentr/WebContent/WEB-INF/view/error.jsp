<%--
documentr - Edit, maintain, and present software documentation on the web.
Copyright (C) 2012 Maik Schreiber

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="dt" tagdir="/WEB-INF/tags" %>

<dt:page>

<div class="page-header"><h1>Error</h1></div>

<p>
<c:choose>
	<c:when test="${!empty messageKey}"><spring:message code="${messageKey}"/></c:when>
	<c:otherwise>
		<%
		request.setAttribute("statusCode", request.getAttribute("javax.servlet.error.status_code")); //$NON-NLS-1$ //$NON-NLS-2$
		%>
		<c:out value="Error ${statusCode}"/>
	</c:otherwise>
</c:choose>
</p>

<p>
<a href="javascript:void(history.go(-1))" class="btn">Go Back</a>
</p>

</dt:page>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="d" uri="http://documentr.org/tld/documentr" %>
<%@ taglib prefix="dt" tagdir="/WEB-INF/tags" %>

<c:set var="pagePathUrl" value="${d:toURLPagePath(pagePath)}"/>
<dt:breadcrumbs>
	<li><a href="<c:url value="/projects"/>"><spring:message code="title.projects"/></a> <span class="divider">/</span></li>
	<li><a href="<c:url value="/project/${projectName}"/>"><c:out value="${projectName}"/></a> <span class="divider">/</span></li>
	<li><a href="<c:url value="/branch/${projectName}/${branchName}"/>"><c:out value="${branchName}"/></a> <span class="divider">/</span></li>
	<li><a href="<c:url value="/page/${projectName}/${branchName}/${pagePathUrl}"/>"><c:out value="${d:getPageTitle(projectName, branchName, pagePath)}"/></a> <span class="divider">/</span></li>
	<li class="active"><spring:message code="title.editAttachment"/></li>
</dt:breadcrumbs>

<dt:page>

<div class="page-header"><h1><spring:message code="title.attachments"/></h1></div>

<c:set var="attachments" value="${d:listPageAttachments(projectName, branchName, pagePath)}"/>
<c:choose>
	<c:when test="${!empty attachments}">
		<ul>
			<c:forEach var="attachment" items="${attachments}">
				<li><c:out value="${attachment}"/></li>
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<p>No attachments found.</p>
	</c:otherwise>
</c:choose>

</dt:page>

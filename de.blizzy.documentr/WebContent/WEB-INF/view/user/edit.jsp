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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="d" uri="http://documentr.org/tld/documentr" %>
<%@ taglib prefix="dt" tagdir="/WEB-INF/tags" %>

<sec:authorize access="hasRole('ROLE_ADMIN')">

<dt:breadcrumbs>
	<li><a href="<c:url value="/users"/>"><spring:message code="title.users"/></a> <span class="divider">/</span></li>
	<li class="active"><spring:message code="title.editUser"/></li>
</dt:breadcrumbs>

<dt:page>

<div class="page-header"><h1><spring:message code="title.editUser"/></h1></div>

<p>
<c:set var="action"><c:url value="/user/save"/></c:set>
<form:form commandName="userForm" action="${action}" method="POST" cssClass="well">
	<c:set var="errorText"><form:errors path="loginName"/></c:set>
	<fieldset class="control-group <c:if test="${!empty errorText}">error</c:if>">
		<form:label path="loginName"><spring:message code="label.loginName"/>:</form:label>
		<form:input path="loginName" cssClass="input-xlarge"/>
		<c:if test="${!empty errorText}"><span class="help-inline"><c:out value="${errorText}" escapeXml="false"/></span></c:if>
	</fieldset>
	<c:set var="errorText1"><form:errors path="password1"/></c:set>
	<c:set var="errorText2"><form:errors path="password2"/></c:set>
	<fieldset class="control-group <c:if test="${!empty errorText1 or !empty errorText2}">error</c:if>">
		<form:label path="password1"><spring:message code="label.password"/>:</form:label>
		<form:password path="password1" cssClass="input-xlarge" autocomplete="off"/>
		<c:if test="${!empty errorText1}"><span class="help-inline"><c:out value="${errorText1}" escapeXml="false"/></span></c:if>
		<form:label path="password2"><spring:message code="label.repeatPassword"/>:</form:label>
		<form:password path="password2" cssClass="input-xlarge" autocomplete="off"/>
		<c:if test="${!empty errorText2}"><span class="help-inline"><c:out value="${errorText2}" escapeXml="false"/></span></c:if>
	</fieldset>
	<fieldset class="control-group">
		<form:label path="disabled">
			<form:checkbox path="disabled"/>
			<spring:message code="label.accountDisabled"/>
		</form:label>
	</fieldset>
	<fieldset class="control-group">
		<form:label path="admin">
			<form:checkbox path="admin"/>
			<spring:message code="label.adminPermissions"/>
		</form:label>
	</fieldset>
	<fieldset>
		<input type="submit" class="btn btn-primary" value="<spring:message code="button.save"/>"/>
		<a href="<c:url value="/users"/>" class="btn"><spring:message code="button.cancel"/></a>
	</fieldset>
</form:form>
</p>

</dt:page>

</sec:authorize>
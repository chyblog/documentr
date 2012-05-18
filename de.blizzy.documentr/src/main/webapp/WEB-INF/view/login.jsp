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

<dt:breadcrumbs>
	<li class="active"><spring:message code="title.login"/></li>
</dt:breadcrumbs>

<dt:page>

<form action="<c:url value="/j_spring_security_check"/>" method="POST" class="well form-horizontal loginForm span4 offset3">
	<fieldset>
		<div class="control-group">
			<label class="control-label"><spring:message code="label.loginName"/>:</label>
			<input type="text" name="j_username" class="input-large"/>
		</div>
		<div class="control-group">
			<label class="control-label"><spring:message code="label.password"/>:</label>
			<input type="password" name="j_password" class="input-large"/>
		</div>
		<div class="form-actions">
			<input type="submit" value="<spring:message code="button.login"/>" class="btn btn-primary"/>
		</div>
	</fieldset>
</form>

</dt:page>

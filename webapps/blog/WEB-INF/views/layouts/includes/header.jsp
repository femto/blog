<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.scooterframework.web.util.W,
                 com.scooterframework.security.LoginHelper"%>

<div id="siteName">
    <h2>Blog</h2>
</div>

<%
    StringBuffer printUrl = new StringBuffer();
    printUrl.append("?printable=true");
    if (request.getQueryString()!=null) {
        printUrl.append('&');
        printUrl.append(request.getQueryString());
    }
%>
<div id="topLinks">
    <p align="right"><%=W.labelLink("Home", "/")%> | <%=W.labelLink("Routes", "/routes")%> | 
	<%if (LoginHelper.isLoggedIn()) {%>
	          <a href="<%=request.getContextPath()%>/signon/logout">Sign Out</a>
	<%} else {%>
	          <a href="<%=request.getContextPath()%>/signon/login">Sign In</a>
	<%}%>
	| <a href="<%= printUrl %>">printable version</a></p>
</div>

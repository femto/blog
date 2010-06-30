<%@ page import="com.scooterframework.common.util.Util" %>
<h1>welcome.sayit view</h1>
<p>${content}</p>
<%= request.getAttribute("content")%>
<%= request.getAttribute("@content")%>

<%=Util.decode("a", "CA=California, NY=New York, VA=Virginia", "Please select a state")%>

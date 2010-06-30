<%@ page import="
        java.util.List,
        com.scooterframework.web.util.O,
        com.scooterframework.web.util.T,
        com.scooterframework.web.util.W,
        com.scooterframework.web.config.Constants"
%>

<%
String recordName = (String)request.getAttribute(Constants.MODEL);
List records = (List)request.getAttribute(recordName + "_list");
%>

<h2><%=T.pluralize(O.count(records), "record")%> for <%=recordName%></h2>

<p class="multilink">
<%=W.labelLink("Add New " + recordName, "add")%>|
<%=W.labelLink("List", "list")%>|
<%=W.labelLink("Paged List", "paged_list")%>
</p>
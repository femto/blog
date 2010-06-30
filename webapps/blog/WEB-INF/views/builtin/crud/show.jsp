<%@ page import="
        java.util.Iterator,
        com.scooterframework.common.util.WordUtil,
        com.scooterframework.orm.activerecord.ActiveRecord,
        com.scooterframework.web.util.O,
        com.scooterframework.web.util.W,
        com.scooterframework.web.config.Constants"
%>

<%
String recordName = (String)request.getAttribute(Constants.MODEL);
ActiveRecord record = (ActiveRecord)request.getAttribute(recordName);
%>

<h2>Show <%=recordName%></h2>

<table class="sTable">
<%for (Iterator it = O.columnNames(record); it.hasNext();) {
    String columnName = (String)it.next();
%>
    <tr>
        <td align="right"><b><%=WordUtil.titleize(columnName)%>:</b></td>
        <td><%=O.hp(record, columnName)%></td>
    </tr>
<%}%>
</table>

<p class="multilink">
<%=W.labelLinkForRecord("Edit", "edit", record)%>|
<%=W.labelLink("List", "list")%>|
<%=W.labelLink("Paged List", "paged_list")%>
</p>
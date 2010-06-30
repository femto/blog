<%@ page import="
        java.util.Iterator,
        com.scooterframework.common.util.WordUtil,
        com.scooterframework.orm.activerecord.ActiveRecord,
        com.scooterframework.orm.sqldataexpress.object.RowInfo,
        com.scooterframework.web.util.O,
        com.scooterframework.web.util.R,
        com.scooterframework.web.util.T,
        com.scooterframework.web.util.W,
        com.scooterframework.web.config.Constants"
%>

<%
String resource = (String)request.getAttribute(Constants.RESOURCE);
String recordName = (String)request.getAttribute(Constants.MODEL);
ActiveRecord record = (ActiveRecord)request.getAttribute(recordName);
%>

<h2>New <%=recordName%></h2>

<%=R.formForAddResource(resource)%>
<table class="sTable">
<%
RowInfo ri = O.rowInfoOf(record);
for (Iterator it = O.columnNames(record); it.hasNext();) {
    String columnName = (String)it.next();
    boolean isAuditedColumn = ri.isAuditedForCreateOrUpdate(columnName);
    if (isAuditedColumn) continue;
    
    Object columnValue = record.getField(columnName);
    boolean isRequired = ri.isRequiredColumn(columnName);
    boolean isLongText = ri.isLongTextColumn(columnName, 255);
    if (isLongText) {
%>
    <tr>
        <td align="right"><%if (isRequired) {%><span class="required">*</span><%}%><b><%=WordUtil.titleize(columnName)%>:</b></td>
        <td><textarea name="<%=columnName%>" cols="60" rows="10"><%=T.text(columnValue)%></textarea></td>
    </tr>
<%  } else { %>
    <tr>
        <td align="right"><%if (isRequired) {%><span class="required">*</span><%}%><b><%=WordUtil.titleize(columnName)%>:</b></td>
        <td><input type="TEXT" name="<%=columnName%>" value="<%=T.text(columnValue)%>" size="80" /></td>
    </tr>
<%
    }
}
%>
    <tr>
        <td colspan="2">
            <input type="submit" value="Create" />
            &nbsp;&nbsp;&nbsp;
            <input type="reset"/>
        </td>
    </tr>
</table>
</form>

<p class="multilink">
<%=W.labelLink("List", R.resourcePath(resource))%>|
<%=W.labelLink("Paged List", R.resourcePath(resource) + "?paged=true")%>
</p>
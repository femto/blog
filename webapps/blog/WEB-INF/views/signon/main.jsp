<%@ page import="
                com.scooterframework.web.util.W"
%>
<h2><%=W.label("welcome.message")%></h2>

<h2>Congratulations! You are now logged in.</h2>

<h4>This is the first page you see after your successful signin.</h4>

<h4>Don't forget to complete authenticate() method of your signon controller.</h4>

<p>If this is not the page you want, you should change the redirect uri in signon controller's authenticate() method and also delete this page.</p>

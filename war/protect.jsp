<%@page import="entity.*"%>
<%
            Users _user = (Users) session.getAttribute("user");
            if (_user == null) {
                response.sendRedirect("index.jsp");
                return;
            }
%>
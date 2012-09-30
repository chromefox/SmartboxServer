package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class logoutServlet extends HttpServlet {
    @Override
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {

      HttpSession session = request.getSession(true);
      session.invalidate();
      response.sendRedirect("index.jsp");
      return;

    }
}

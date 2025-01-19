package com.sh.ytb.inactive.archive;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.Profile;

/* Spring Session이 따르고 있는 HttpSession 표준 방식 */
@Profile("archive")
@WebServlet("/session")
public class SessionServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String attributeName = req.getParameter("attributeName");
    String attributeValue = req.getParameter("attributeValue");
    req.getSession().setAttribute(attributeName, attributeValue);
    resp.sendRedirect(req.getContextPath() + "/");
  }

  private static final long serialVersionUID = 2878267318695777395L;

}

package bulletinBoard.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.User;

@WebFilter("/*")
public class LoginFilter implements Filter{

  public void doFilter(ServletRequest request, ServletResponse response,
		  FilterChain chain) throws IOException, ServletException {

      String target = ((HttpServletRequest)request).getRequestURI();
      String[] urlBody = target.split("/");
      //要素数から数えるので-1しないと全ての要素が取れない
      if(!urlBody[urlBody.length - 1].matches("login")) {
          User user = (User) ((HttpServletRequest)request).getSession().getAttribute("loginUser");
          if (user == null) {
        	  ((HttpServletResponse)response).sendRedirect("./login");
        	  return;
          }
      }

		chain.doFilter(request, response);
  }

  public void init(FilterConfig filterConfig) throws ServletException{
  }

  public void destroy(){
  }
}
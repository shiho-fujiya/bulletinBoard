package bulletinBoard.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bulletinBoard.beans.User;

@WebFilter("/*")
public class AuthorityFilter implements Filter{

  public void doFilter(ServletRequest request, ServletResponse response,
		  FilterChain chain) throws IOException, ServletException {

	  String target = ((HttpServletRequest)request).getRequestURI();
	  //System.out.println(target);
	  String[] urlBody = target.split("/");

	  //要素数から数えるので-1しないと全ての要素が取れない
	  if(urlBody[urlBody.length - 1].matches("management|settings|signup")) {
		  User user = (User) ((HttpServletRequest)request).getSession().getAttribute("loginUser");
		  if (user != null && user.getPositionId() != 1) {
			  List<String> messages = new ArrayList<String>();
			  HttpSession session = ((HttpServletRequest)request).getSession();
			  messages.add("権限がありません");
			  session.setAttribute("errorMessages", messages);
			  ((HttpServletResponse)response).sendRedirect("./");
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
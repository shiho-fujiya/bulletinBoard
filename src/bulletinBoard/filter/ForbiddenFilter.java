/*package bulletinBoard.filter;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/*")
public class ForbiddenFilter implements Filter {

	public void init(FilterConfig objFc){}
	  public void destroy(){}
	  public void doFilter(ServletRequest request, ServletResponse response,
	FilterChain chain) throws ServletException, IOException {

	    try{
	      boolean flag=false;
	      Class.forName("org.gjt.mm.mysql.Driver");
	      Connection db=DriverManager.getConnection("jdbc:mysql://localhost/
	atit?user=root&password=root&useUnicode=true&characterEncoding=Shift_JIS");
	      objSql.setString(1,((HttpServletRequest)request).getServletPath());
	      ResultSet rs=objSql.executeQuery();
	      if(rs.next()){
	        StringTokenizer objTkn=new StringTokenizer(rs.getString("role"),",");
	        while(objTkn.hasMoreTokens()){
	          if(((HttpServletRequest)request).isUserInRole(objTkn.nextToken())){
	            flag=true;
	            break;
	          }
	        }
	      }else{
	        flag=true;
	      }
	      rs.close();
	      objSql.close();
	      db.close();
	      if(!flag){
	        ((HttpServletResponse)response).sendError
	(HttpServletResponse.SC_FORBIDDEN);
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	    chain.doFilter(request,response);
	  }
}
*/
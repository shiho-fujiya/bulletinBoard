package bulletinBoard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.Post;
import bulletinBoard.beans.User;
import bulletinBoard.beans.UserComment;
import bulletinBoard.beans.UserPost;
import bulletinBoard.service.CommentService;
import bulletinBoard.service.PostService;

@WebServlet(urlPatterns = { "/index.jsp", "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


		User user = (User) request.getSession().getAttribute("loginUser");
		String category = request.getParameter("category");
		String oldDay = request.getParameter("oldDay");
		//System.out.println(oldDay);
		String oldDate = request.getParameter("oldDay");
		String newDate = request.getParameter("newDay");

		List<UserComment> comments = new CommentService().getPost(null);
		List<UserPost> posts = new PostService().getPost(category, oldDate, newDate);














		request.setAttribute("users", user);
		request.setAttribute("posts", posts);

		request.setAttribute("user", user);
		request.setAttribute("comments", comments);

		List<Post> categoris = new PostService().getCategoris();
		request.setAttribute("categoris", categoris);
		request.setAttribute("setCategory", category);

		UserPost oldDays = new PostService().getOldDays();
		request.setAttribute("oldDays", oldDays);
		request.setAttribute("setOldDay", oldDay);

		if (oldDay == null || oldDay == "") {
			oldDay = request.getParameter("oldDay");
			new PostService().getCategoris();
		}

		Date date = new Date();
		request.setAttribute("date", date);
		//System.out.println(date.toString());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String formatDate = sdf.format(date);
		System.out.println(formatDate);

		if (StringUtils.isEmpty(formatDate)) {
			formatDate = request.getParameter("today");
		}

		request.getRequestDispatcher("/home.jsp").forward(request, response);

	}
}


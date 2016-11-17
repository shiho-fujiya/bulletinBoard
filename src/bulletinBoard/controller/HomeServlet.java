package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		//String userId = request.getParameter("user_id");
		//System.out.println("aaaaaaaa");

		User user = (User) request.getSession().getAttribute("loginUser");
		List<UserPost> posts;

		String category = request.getParameter("category");

		System.out.println(category);

		posts = new PostService().getPost(category);
		request.setAttribute("users", user);
		//System.out.println(user);
		request.setAttribute("posts", posts);
		//System.out.println(posts);

		List<UserComment> comments = new CommentService().getPost(null);

		request.setAttribute("user", user);
		request.setAttribute("comments", comments);

		List<Post> categoris = new PostService().getCategoris();
		//System.out.println(categoris.size());
		request.setAttribute("categoris", categoris);
		request.setAttribute("setCategory", category);
		//System.out.println(categoris);

		request.getRequestDispatcher("/home.jsp").forward(request, response);

	}
}


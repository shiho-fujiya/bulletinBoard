package bulletinBoard.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.Post;
import bulletinBoard.beans.User;
import bulletinBoard.beans.UserPost;
import bulletinBoard.service.PostService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/newPost" })
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String userId = request.getParameter("user_id");

		User user;
		List<UserPost> posts;
		boolean isShowPostForm;

		if (userId == null) {
			user = (User) request.getSession().getAttribute("loginUser");
			posts = new PostService().getPost(null);
			if (user != null) {
				isShowPostForm = true;
			} else {
				isShowPostForm = false;
			}
		} else {
			int uId = Integer.parseInt(userId);
			user = new UserService().getUser(uId);
			posts = new PostService().getPost(uId);
			isShowPostForm = false;
		}
		request.setAttribute("user", user);
		request.setAttribute("posts", posts);
		request.setAttribute("isShowPostForm", isShowPostForm);

		request.getRequestDispatcher("/post.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		if (isValid(request, messages) == true) {

			User user = (User) session.getAttribute("loginUser");

			Post post = new Post();
			post.setSubject(request.getParameter("subject"));
			post.setCategory(request.getParameter("category"));
			post.setText(request.getParameter("text"));
			post.setUserId(user.getId());

			new PostService().register(post);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String subject = request.getParameter("subject");
		String category = request.getParameter("category");
		String text = request.getParameter("text");

		if (StringUtils.isEmpty(subject) == true) {
			messages.add("入力してください");
		}
		if (1000 < subject.length()) {
			messages.add("50文字以下で入力してください");
		}
		if (StringUtils.isEmpty(category) == true) {
			messages.add("入力してください");
		}
		if (1000 < category.length()) {
			messages.add("10文字以下で入力してください");
		}
		if (StringUtils.isEmpty(text) == true) {
			messages.add("入力してください");
		}
		if (1000 < text.length()) {
			messages.add("1000文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
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
import bulletinBoard.service.PostService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/post" })
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String userId = request.getParameter("user_id");

		User user;
		//List<UserPost> posts;
		boolean isShowPostForm;
		//Post post = new Post();

		if (userId == null) {
			user = (User) request.getSession().getAttribute("loginUser");
			//posts = new PostService().getPost(null, post);
			if (user != null) {
				isShowPostForm = true;
			} else {
				isShowPostForm = false;
			}
		} else {
			int uId = Integer.parseInt(userId);
			user = new UserService().getUser(uId);
			//posts = new PostService().getPost(uId, post);
			isShowPostForm = false;
		}
		request.setAttribute("user", user);
		//request.setAttribute("posts", posts);
		request.setAttribute("isShowPostForm", isShowPostForm);

		request.getRequestDispatcher("/post.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();

		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");
		Post post = new Post();
		post.setSubject(request.getParameter("subject"));
		post.setCategory(request.getParameter("category"));
		post.setText(request.getParameter("text"));
		post.setUserId(user.getId());

		if (isValid(request, messages) == true) {
			new PostService().register(post);
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("post", post);
			request.getRequestDispatcher("post.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String subject = request.getParameter("subject");
		//System.out.println(subject);
		String category = request.getParameter("category");
		//System.out.println(category);
		String text = request.getParameter("text");
		//System.out.println(text);

		if (StringUtils.isEmpty(subject) == true || StringUtils.isBlank(subject)) {
			messages.add("件名を入力してください");
		} else if (50< subject.length()) {
			messages.add("件名は50文字以下で入力してください");
		}

		if (StringUtils.isEmpty(text) == true || StringUtils.isBlank(text)) {
			messages.add("本文を入力してください");
		} else if (1000 < text.length()) {
			messages.add("本文は1000文字以下で入力してください");
		}

		if (StringUtils.isEmpty(category) == true  || StringUtils.isBlank(category)) {
			messages.add("カテゴリーを入力してください");
		} else if (10 < category.length()) {
			messages.add("カテゴリーは10文字以下で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
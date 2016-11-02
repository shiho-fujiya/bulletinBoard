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

@WebServlet(urlPatterns = { "/newMessage" })
public class NewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		String message = request.getParameter("message");

		if (StringUtils.isEmpty(message) == true) {
			messages.add("入力してください");
		}
		if (1000 < message.length()) {
			messages.add("1000文字以下で入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
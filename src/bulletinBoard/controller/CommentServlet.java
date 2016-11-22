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

import bulletinBoard.beans.Comment;
import bulletinBoard.beans.User;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = { "/comment" })
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		User user = (User) session.getAttribute("loginUser");
		Comment comment = new Comment();
		comment.setText(request.getParameter("text"));
		comment.setPostId(Integer.parseInt(request.getParameter("postId")));
		comment.setUserId(user.getId());

		if (isValid(request, messages) == true) {
			new CommentService().register(comment);
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("comment", comment);
			response.sendRedirect("./");
		}

	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String text = request.getParameter("text");
		//System.out.println(text);

		if (StringUtils.isEmpty(text) == true  || StringUtils.isBlank(text)) {
			messages.add("コメントを入力してください");
		} else if (500 < text.length()) {
			messages.add("コメントは500文字以下で入力してください");
		}

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
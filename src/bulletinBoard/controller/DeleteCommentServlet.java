package bulletinBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.Comment;
import bulletinBoard.service.CommentService;

@WebServlet(urlPatterns = { "/deletecomment" })

public class DeleteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Comment comment = new Comment();
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		//System.out.println(commentId);
		comment.setId(commentId);

		new CommentService().delete(comment);
//
		response.sendRedirect("./");
		//System.out.println(user);
	}

}

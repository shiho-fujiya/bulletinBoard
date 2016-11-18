package bulletinBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.Post;
import bulletinBoard.service.PostService;

//urlPatternsは何でもいい、jspの事ではない
@WebServlet(urlPatterns = { "/deletepost" })
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		Post post = new Post();
		//getParameter忘れないで!!
		int postId = Integer.parseInt(request.getParameter("postId"));
		post.setId(postId);

		new PostService().delete(post);

		response.sendRedirect("./");
		//System.out.println(user);
	}

}

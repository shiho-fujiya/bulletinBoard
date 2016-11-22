package bulletinBoard.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
		// JSPから値を取得
		String category = request.getParameter("category");
		String oldDate = request.getParameter("oldDate");
		String newDate = request.getParameter("newDate");

		// 最小の日付が入力されているか判定
		if(StringUtils.isEmpty(oldDate)) {
			// ユーザーが日付を入力してないのでDBから日付を取得
			UserPost post = new PostService().getOldDate();
			if (post != null) {
				oldDate = new SimpleDateFormat("yyyy/MM/dd").format(post.getInsertDate());
			} else {
				oldDate = "";
			}
		}
		// 最大の日付が入力されているか判定
		if(StringUtils.isEmpty(newDate)) {
			// ユーザーが日付を入力してないのでDBから日付を取得
			UserPost post = new PostService().getNewDate();
			if (post != null) {
				newDate = new SimpleDateFormat("yyyy/MM/dd").format(post.getInsertDate());
			} else {
				newDate = "";
				System.out.println(newDate);
			}
		}

		// 投稿を絞り込む
		List<UserPost> posts = new PostService().getPost(category, oldDate, newDate);
		// カテゴリー一覧を取得
		List<Post> categoris = new PostService().getCategoris();
		// コメント一覧を取得
		List<UserComment> comments = new CommentService().getUserComments();
		// JSPに表示するように値をセット
		request.setAttribute("categoris", categoris);
		request.setAttribute("setCategory", category);
		request.setAttribute("oldDate", oldDate);
		request.setAttribute("newDate", newDate);
		request.setAttribute("posts", posts);
		request.setAttribute("user", user);
		request.setAttribute("comments", comments);
		request.setAttribute("loginUser", user);

		request.getRequestDispatcher("/home.jsp").forward(request, response);

	}
}

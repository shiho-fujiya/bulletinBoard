package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.Branches;
import bulletinBoard.beans.User;
import bulletinBoard.service.BranchesService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/management"})
public class ManagementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		List<User> users = new UserService().getUsers();
		//boolean isShowPostsForm;
		List<Branches> branches = new BranchesService().getBranches();

		//System.out.println(users);
		request.setAttribute("users", users);
		request.setAttribute("branches", branches);

		request.getRequestDispatcher("/management.jsp").forward(request, response);

	}


}

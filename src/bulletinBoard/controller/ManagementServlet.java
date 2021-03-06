package bulletinBoard.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bulletinBoard.beans.Branches;
import bulletinBoard.beans.Positions;
import bulletinBoard.beans.User;
import bulletinBoard.service.BranchesService;
import bulletinBoard.service.ManagementService;
import bulletinBoard.service.PositionsService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/management"})
public class ManagementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		List<User> users = new UserService().getUsers();
		//boolean isShowPostsForm;
		List<Branches> branches = new BranchesService().getBranches();
		List<Positions> positions = new PositionsService().getPositions();

		//System.out.println(users);
		request.setAttribute("users", users);
		request.setAttribute("branches", branches);
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("/management.jsp").forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		boolean operation = new Boolean(request.getParameter("operation")).booleanValue();
		//System.out.println(operation);
		int id = Integer.parseInt(request.getParameter("id"));
		//System.out.println(id);

		new ManagementService().updateBool(id, operation);
		response.sendRedirect("management");
	}
}

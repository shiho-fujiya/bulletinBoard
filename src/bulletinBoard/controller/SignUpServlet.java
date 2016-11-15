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

import bulletinBoard.beans.Branches;
import bulletinBoard.beans.Positions;
import bulletinBoard.beans.User;
import bulletinBoard.service.BranchesService;
import bulletinBoard.service.PositionsService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		List<Branches> branches = new BranchesService().getBranches();
		List<Positions> positions = new PositionsService().getPositions();

		//System.out.println(branches.size());
		request.setAttribute("branches", branches);
		request.setAttribute("positions", positions);
		request.getRequestDispatcher("signup.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		List<String> messages = new ArrayList<String>();

		HttpSession session = request.getSession();
		if (isValid(request, messages) == true) {

			User user = new User();
			user.setAccount(request.getParameter("account"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("name"));
			user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
			user.setPositionId(Integer.parseInt(request.getParameter("positionId")));

			new UserService().register(user);

			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("signup");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String name =request.getParameter("name");
		String branchId = request.getParameter("branchId");
		String positionId =request.getParameter("positionId");

		if (StringUtils.isEmpty(account) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}
		if (StringUtils.isEmpty(branchId) == true) {
			messages.add("勤務先を選択してください");
		} else {
			Integer.parseInt(request.getParameter("branchId"));
		}
		if (StringUtils.isEmpty(positionId) == true) {
			messages.add("部署・役職を選択してください");
		} else {
			Integer.parseInt(request.getParameter("positionId"));
		}

		//int branchId = Integer.parseInt(request.getParameter("branchId"));
		//後でバリデーションでHTMLをいじられないようにする
		//int positionId = Integer.parseInt(request.getParameter("positionId"));
		//後でバリデーションでHTMLをいじられないようにする

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}


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
import bulletinBoard.service.SettingService;
import bulletinBoard.service.UserService;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String id = request.getParameter("userId");

		if (!id.matches("^[0-9]$")) {
			response.sendRedirect("management");
			List<String> messages = new ArrayList<String>();
			messages.add("無効なIDです");
			session.setAttribute("errorMessages", messages);
			return;
		}
		int userId = Integer.parseInt(id);



		SettingService settingService = new SettingService();
		User editUser = settingService.settings(userId);
		//System.out.println(userId);
		//System.out.println(editUser.getName());
		List<Branches> branches = new BranchesService().getBranches();
		List<Positions> positions = new PositionsService().getPositions();

		session.setAttribute("editUser", editUser);
		request.setAttribute("userId", userId);
		request.setAttribute("user", editUser);
		request.setAttribute("branches", branches);
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		User editUser = getEditUser(request);
		session.setAttribute("editUser", editUser);

		editUser.setAccount(request.getParameter("account"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setName(request.getParameter("name"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		editUser.setPositionId(Integer.parseInt(request.getParameter("positionId")));

		//System.out.println(user);
		session.setAttribute("user", editUser);

		if (isValid(request, messages) == true) {

			new UserService().update(editUser);

			response.sendRedirect("management");
		} else {
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("settings?userId=" + editUser.getId());
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		User editUser = (User) session.getAttribute("editUser");
		session.setAttribute("userId", editUser);
		//System.out.println(user);

		editUser.setName(request.getParameter("name"));
		editUser.setAccount(request.getParameter("account"));
		editUser.setPassword(request.getParameter("password"));
		editUser.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		editUser.setPositionId(Integer.parseInt(request.getParameter("positionId")));
		return editUser;

	}


	private boolean isValid(HttpServletRequest request, List<String> messages) {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		String confirmation = request.getParameter("confirmation");
		String name =request.getParameter("name");
		String branchId = request.getParameter("branchId");
		String positionId =request.getParameter("positionId");

		if (StringUtils.isEmpty(account) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (password != confirmation) {
			messages.add("パスワードが一致していません");
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
		//if (.matches("^[0-9]{8}$"))

		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
}
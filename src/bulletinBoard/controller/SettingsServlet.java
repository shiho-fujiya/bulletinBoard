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

import bulletinBoard.beans.User;
import bulletinBoard.service.SettingService;

@WebServlet(urlPatterns = { "/settings" })
public class SettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int userId = Integer.parseInt(request.getParameter("userId"));
		SettingService settingService = new SettingService();
		User user = settingService.settings(userId);
		System.out.println(userId);
		System.out.println(user.getName());

		request.setAttribute("userId", userId);
		request.setAttribute("user", user);

		request.getRequestDispatcher("/settings.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int userId = Integer.parseInt(request.getParameter("id"));

		SettingService settingService = new SettingService();
		User user = settingService.settings(userId);
		HttpSession session = request.getSession();
		if (user != null) {

			session.setAttribute("user", user);

			response.sendRedirect("management");

		} else {

			List<String> messages = new ArrayList<String>();
			session.setAttribute("errorMessages", messages);
			session.setAttribute("userId", userId);
			session.removeAttribute("user");

			response.sendRedirect("settings");
		}
	}

	private User getEditUser(HttpServletRequest request)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		User editUser = (User) session.getAttribute("editUser");

		editUser.setName(request.getParameter("name"));
		editUser.setAccount(request.getParameter("account"));
		editUser.setPassword(request.getParameter("password"));
		return editUser;
	}

	private boolean isValid(HttpServletRequest request, List<String> messages) {

		String name =request.getParameter("name");
		String account = request.getParameter("account");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(name) == true) {
			messages.add("名前を入力してください");
		}
		if (StringUtils.isEmpty(account) == true) {
			messages.add("アカウント名を入力してください");
		}
		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
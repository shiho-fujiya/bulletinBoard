package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.beans.User;
import bulletinBoard.dao.UserDao;
import bulletinBoard.utils.CipherUtil;

public class LoginService {

	public User login(String account, String password) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection, account, encPassword);

			commit(connection);

			return user;
		} catch (RuntimeException e) {
			//System.out.println("a");
			rollback(connection);
			throw e;
		} catch (Error e) {
			//System.out.println("b");
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}

}

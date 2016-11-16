package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;

import bulletinBoard.dao.UserDao;


public class ManagementService {

	public Boolean updateBool(Integer userId, boolean operation) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			Boolean ret = userDao.updateBool(connection, userId, operation);

			commit(connection);

			return true;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}

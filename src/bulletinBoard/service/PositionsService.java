package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Positions;
import bulletinBoard.dao.PositionsDao;

public class PositionsService {
	public List<Positions> getPositions() {

		Connection connection = null;
		try {
			connection = getConnection();

			PositionsDao positionsDao = new PositionsDao();
			List<Positions> ret = positionsDao.getPositions(connection);

			commit(connection);
			System.out.println(ret);
			return ret;
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

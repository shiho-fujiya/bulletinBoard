package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Branches;
import bulletinBoard.exception.SQLRuntimeException;

public class BranchesDao {

	public List<Branches> getBranches(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM branches ");

			ps = connection.prepareStatement(sql.toString());
			System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			List<Branches> ret = toBranchesList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Branches> toBranchesList(ResultSet rs)
			throws SQLException {

		List<Branches> ret = new ArrayList<Branches>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				Branches branches = new Branches();
				branches.setName(name);
				branches.setId(id);
				branches.setInsertDate(insertDate);

				ret.add(branches);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}

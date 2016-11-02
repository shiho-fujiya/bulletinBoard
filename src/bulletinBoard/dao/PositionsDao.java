package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Positions;
import bulletinBoard.exception.SQLRuntimeException;

public class PositionsDao {

	public List<Positions> getPositions(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM positions ");

			ps = connection.prepareStatement(sql.toString());
			System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			List<Positions> ret = toPositionsList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Positions> toPositionsList(ResultSet rs)
			throws SQLException {

		List<Positions> ret = new ArrayList<Positions>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				Positions positions = new Positions();
				positions.setName(name);
				positions.setId(id);
				positions.setInsertDate(insertDate);

				ret.add(positions);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
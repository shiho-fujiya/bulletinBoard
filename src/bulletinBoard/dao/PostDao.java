package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Post;
import bulletinBoard.exception.SQLRuntimeException;

public class PostDao {

	public void insert(Connection connection, Post post) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO posts ( ");
			sql.append("user_id");
			sql.append(", subject");
			sql.append(", category");
			sql.append(", text");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, post.getUserId());
			ps.setString(2, post.getSubject());
			ps.setString(3, post.getCategory());
			ps.setString(4, post.getText());
			//System.out.println(ps.toString());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, Post post) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM posts ");
			sql.append(" WHERE");
			sql.append(" id = ?");
			//System.out.println(sql);
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, post.getId());
			//System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public List<Post> getCategoris(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT category FROM posts GROUP BY category");
			//System.out.println(sql);
			ps = connection.prepareStatement(sql.toString());
			//System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			//System.out.println(rs);

			List<Post> ret = toCategorisList(rs);
			//System.out.println(ret);

			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Post> toCategorisList(ResultSet rs)
			throws SQLException {

		List<Post> ret = new ArrayList<Post>();
		try {
			while (rs.next()) {
				String category = rs.getString("category");
				//System.out.println(category);

				Post categoris = new Post();
				categoris.setCategory(category);

				ret.add(categoris);
			}
			return ret;
		} finally {
			close(rs);
		}
	}
}
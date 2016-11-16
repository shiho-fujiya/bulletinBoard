package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Post;
import bulletinBoard.exception.SQLRuntimeException;

public class PostDao {

	public Post getPost(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE (account = ?) AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);


			ResultSet rs = ps.executeQuery();
			List<Post> postList = toPostList(rs);
			if (postList.isEmpty() == true) {
				return null;
			} else if (2 <= postList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return postList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Post> toPostList(ResultSet rs) throws SQLException {

		List<Post> ret = new ArrayList<Post>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String subject = rs.getString("subject");
				String category = rs.getString("category");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				Post post = new Post();
				post.setId(id);
				post.setSubject(subject);
				post.setCategory(category);
				post.setText(text);
				post.setInsertDate(insertDate);
				post.setUpdateDate(updateDate);

				ret.add(post);
				//System.out.println(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

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
			System.out.println(sql);
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, post.getId());
			System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}
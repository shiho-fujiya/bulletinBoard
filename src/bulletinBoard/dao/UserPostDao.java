package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UserPost;
import bulletinBoard.exception.SQLRuntimeException;

public class UserPostDao {

	public List<UserPost> getUserPost(Connection connection, Integer userId, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_posts ");
			if (userId != null) {
				sql.append("SELECT * FROM user_id = ?");
			}
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			if (userId != null) {
				ps.setInt(1, userId);
			}

			ResultSet rs = ps.executeQuery();
			List<UserPost> ret = toUserPostList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserPost> toUserPostList(ResultSet rs)
			throws SQLException {

		List<UserPost> ret = new ArrayList<UserPost>();
		try {
			while (rs.next()) {
				int postId = rs.getInt("post_id");
				String name = rs.getString("name");
				int userId = rs.getInt("user_id");
				String subject = rs.getString("subject");
				String category = rs.getString("category");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserPost post = new UserPost();
				post.setId(postId);
				post.setName(name);
				post.setUserId(userId);
				post.setSubject(subject);
				post.setCategory(category);
				post.setText(text);
				post.setInsertDate(insertDate);

				ret.add(post);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}

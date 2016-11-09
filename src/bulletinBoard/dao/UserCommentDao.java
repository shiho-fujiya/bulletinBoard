package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.UserComment;
import bulletinBoard.exception.SQLRuntimeException;

public class UserCommentDao {

	public List<UserComment> getUserComment(Connection connection, Integer userId, int num) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_comments ");
			sql.append("ORDER BY insert_date DESC limit " + num);

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs)
			throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int userId = rs.getInt("user_id");
				int postId = rs.getInt("post_id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserComment comment = new UserComment();
				comment.setName(name);
				comment.setUserId(userId);
				comment.setPostId(postId);
				comment.setText(text);
				comment.setInsertDate(insertDate);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}
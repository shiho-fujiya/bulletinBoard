package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bulletinBoard.beans.Comment;
import bulletinBoard.exception.SQLRuntimeException;

public class CommentDao {

	public Comment getComment(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users WHERE (account = ?) AND password = ?";

			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);


			ResultSet rs = ps.executeQuery();
			List<Comment> commentList = toCommentList(rs);
			if (commentList.isEmpty() == true) {
				return null;
			} else if (2 <= commentList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			} else {
				return commentList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<Comment> toCommentList(ResultSet rs) throws SQLException {

		List<Comment> ret = new ArrayList<Comment>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Timestamp updateDate = rs.getTimestamp("update_date");

				Comment comment = new Comment();
				comment.setId(id);
				comment.setText(text);
				comment.setInsertDate(insertDate);
				comment.setUpdateDate(updateDate);

				ret.add(comment);
				//System.out.println(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append("post_id");
			sql.append(", user_id");
			sql.append(", text");
			sql.append(", insert_date");
			sql.append(", update_date");
			sql.append(") VALUES (");
			sql.append("?");
			sql.append(", ?");
			sql.append(", ?");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(", CURRENT_TIMESTAMP");
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getPostId());
			ps.setInt(2, comment.getUserId());
			ps.setString(3, comment.getText());
			System.out.println(ps.toString());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM comments ");
			sql.append(" WHERE");
			sql.append(" id = ?");
			//System.out.println(sql);
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getId());
			//System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}

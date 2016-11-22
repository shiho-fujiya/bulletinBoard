package bulletinBoard.dao;

import static bulletinBoard.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import bulletinBoard.beans.UserPost;
import bulletinBoard.exception.SQLRuntimeException;

public class UserPostDao {

	public List<UserPost> getUserPost(Connection connection, String category, String oldDate, String newDate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_posts ");
			sql.append("WHERE insert_date BETWEEN ? AND ? ");
			if (!StringUtils.isEmpty(category) == true) {
				sql.append(" and category = ? ");
			}
			sql.append("ORDER BY insert_date DESC ");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, oldDate + " 00:00:00");//値の引数
			ps.setString(2, newDate + " 23:59:59");//値の引数
			if (!StringUtils.isEmpty(category) == true) {
				ps.setString(3, category);
			}
			//System.out.println(ps);


			//System.out.println(ps.toString());

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
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				String subject = rs.getString("subject");
				String category = rs.getString("category");
				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserPost post = new UserPost();
				post.setId(postId);
				post.setName(name);
				post.setUserId(userId);
				post.setBranchId(branchId);
				post.setPositionId(positionId);
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

	public UserPost getOldDate(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_posts ORDER BY insert_date LIMIT 1 ");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<UserPost> ret = toUserPostList(rs);

			if (ret.size() == 0) {
				return null;
			} else {
				return ret.get(0);
			}

		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	public UserPost getNewDate(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_posts ORDER BY insert_date DESC LIMIT 1 ");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<UserPost> ret = toUserPostList(rs);

			if (ret.size() == 0) {
				return null;
			} else {
				return ret.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}

package bulletinBoard.service;

import static bulletinBoard.utils.CloseableUtil.*;
import static bulletinBoard.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bulletinBoard.beans.Post;
import bulletinBoard.beans.UserPost;
import bulletinBoard.dao.PostDao;
import bulletinBoard.dao.UserPostDao;

public class PostService {

	private static final int LIMIT_NUM = 1000;

	public void register(Post post) {

		Connection connection = null;
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			postDao.insert(connection, post);

			commit(connection);
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
	public List<UserPost> getPost(Integer userId) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao postDao = new UserPostDao();
			List<UserPost> ret = postDao.getUserPost(connection, userId, LIMIT_NUM);

			commit(connection);

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


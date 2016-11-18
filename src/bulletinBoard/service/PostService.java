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

	public List<UserPost> getPost(String category, String oldDate, String newDate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao postDao = new UserPostDao();
			List<UserPost> ret = postDao.getUserPost(connection, category, oldDate, newDate);


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

	public void delete(Post post) {

		Connection connection = null;

		//System.out.println(post);
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			postDao.delete(connection, post);

			commit(connection);

			return;
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

	public List<Post> getCategoris() {

		Connection connection = null;
		try {
			connection = getConnection();

			PostDao postDao = new PostDao();
			List<Post> ret = postDao.getCategoris(connection);

			commit(connection);
			//System.out.println(ret);
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

	public UserPost getOldDays() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao userPostDao = new UserPostDao();
			UserPost ret = userPostDao.getOldDate(connection);

			commit(connection);
			//System.out.println(ret);
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

	public List<UserPost> getDays() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserPostDao userPostDao = new UserPostDao();
			List<UserPost> ret = userPostDao.getDays(connection);

			commit(connection);
			//System.out.println(ret);
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


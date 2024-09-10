package me.dgeniust.dao;

import me.dgeniust.dao.impl.IUserDAO;
import me.dgeniust.dao.impl.UserDAOImpl;
import me.dgeniust.models.UserModel;
import me.dgeniust.services.IUserService;

public class UserServiceImpl implements IUserService {

	IUserDAO userDao = new UserDAOImpl();

	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.get(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public boolean register(String username, String email, String password, String fullname) {

		userDao.insert(new UserModel(username, email, password, fullname));
		return true;
	}

	@Override
	public boolean checkExistEmail(String email) {
		return userDao.checkExistEmail(email);
	}

	@Override
	public boolean checkExistUsername(String username) {
		return userDao.checkExistUsername(username);
	}

	@Override
	public UserModel get(String username) {
		return userDao.findByUserName(username);
	}

}
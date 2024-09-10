package me.dgeniust.services;

import me.dgeniust.models.UserModel;

public interface IUserService {
	
	public UserModel login(String username, String password);

	public UserModel get(String username);

	boolean register(String username, String email, String password, String fullname);

	boolean checkExistEmail(String email);

	boolean checkExistUsername(String username);

}
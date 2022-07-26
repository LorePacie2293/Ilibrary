package com.Ilibrary.BooksService.Service;

import java.util.List;

import com.Ilibrary.BooksService.Entity.Users;

public interface UserService {

	List<Users> selUsersAll();
	Users registerUser(Users user);
	Users insertUser(Users user);
	Users selUserByUsername(String username);
	Users selUserByEmail(String email);
	void removeUser(Users user);
}

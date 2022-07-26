package com.Ilibrary.BooksService.Service;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Repository.CartsRepository;
import com.Ilibrary.BooksService.Repository.UsersRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private CartsRepository cartRepository;
	
	@Autowired
	private UsersRepository userRepository;
	
	@Override
	@Transactional
	public Users registerUser(Users user) {
		
		Users newUser = user;
		Carts cart = new Carts();
		cart = cartRepository.save(cart);
		
		//Imposta relazione
		newUser.setCart(cart);
		cart.setUser(newUser);
		
		return userRepository.save(newUser);
	}

	
	@Override
	public Users selUserByUsername(String username) {
		
		return userRepository.findByusername(username);
		
	}


	@Override
	@Transactional
	public void removeUser(Users user) {
		
		userRepository.delete(user);
		
	}


	@Override
	public List<Users> selUsersAll() {
		
		return userRepository.findAll();
	}


	@Override
	public Users selUserByEmail(String email) {
		
		return userRepository.findByuserEmail(email);
	}


	@Override
	@Transactional
	public Users insertUser(Users user) {
		
		return userRepository.save(user);
	}

}

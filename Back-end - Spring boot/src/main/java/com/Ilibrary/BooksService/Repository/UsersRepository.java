package com.Ilibrary.BooksService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ilibrary.BooksService.Entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{

	public Users findByusername(String username);
	public Users findById(int userId);
	public Users findByuserEmail(String email);
	public boolean existsByuserEmail(String email);
}

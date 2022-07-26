package com.Ilibrary.BooksService.Service;

import com.Ilibrary.BooksService.Entity.Carts;
import com.Ilibrary.BooksService.Entity.Users;

public interface CartsService {

	Carts selCartByUser(Users user);
	Carts insertcart(Carts cart);
	void removeCarts(Carts cart);
	Carts removeAllBooksByCarts(Carts cart, Users user);
	boolean isExistBooks(Carts cart);
}

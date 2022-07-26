package com.Ilibrary.BooksService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Ilibrary.BooksService.Entity.Images;

public interface ImagesRepository extends JpaRepository<Images, Long>{
	
	Optional<Images> findByName(String name);

}

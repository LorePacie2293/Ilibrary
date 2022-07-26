package com.Ilibrary.BooksService.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Ilibrary.BooksService.Entity.Images;
import com.Ilibrary.BooksService.Entity.Users;
import com.Ilibrary.BooksService.Exception.ErrorResponse;
import com.Ilibrary.BooksService.Repository.ImagesRepository;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api")
public class ImagesController {

	@Autowired
	ImagesRepository imageRepository;

	@PostMapping("/images/upload")
	public ResponseEntity<ErrorResponse> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		Images img = new Images(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		
		//Controlla se l'img è gia inserita
		Optional<Images> checkImage = imageRepository.findByName(file.getOriginalFilename());
		if(checkImage.isEmpty()){
			imageRepository.save(img);
		}
		
		ErrorResponse msg = new ErrorResponse(new Date(), "Upload completato con successo");
		return new ResponseEntity<ErrorResponse>(msg, HttpStatus.OK);
	}
	
	@GetMapping("/images/get/{imageName}")
	public Images getImage(@PathVariable("imageName") String imageName) throws IOException {

		final Optional<Images> retrievedImage = imageRepository.findByName(imageName);
		Images img = new Images(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
	}
	
	// COMPRESSIONE IMG
	public static byte[] compressBytes(byte[] data) {
		
			Deflater deflater = new Deflater();
	        deflater.setLevel(Deflater.BEST_COMPRESSION);
	        deflater.setInput(data);
	        deflater.finish();

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	        byte[] tmp = new byte[4*1024];
	        while (!deflater.finished()) {
	            int size = deflater.deflate(tmp);
	            outputStream.write(tmp, 0, size);
	        }
	        try {
	            outputStream.close();
	        } catch (Exception e) {
	        }
	        return outputStream.toByteArray();
	}
		
	// DECOMPRESSIONE IMG
	public static byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
	        inflater.setInput(data);
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
	        byte[] tmp = new byte[4*1024];
	        try {
	            while (!inflater.finished()) {
	                int count = inflater.inflate(tmp);
	                outputStream.write(tmp, 0, count);
	            }
	            outputStream.close();
	        } catch (Exception exception) {
	        }
	        return outputStream.toByteArray();
	}
}


package com.Ilibrary.BooksService.controllerPost.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.Ilibrary.BooksService.IlibraryBooksServiceApplication;


@ContextConfiguration(classes = IlibraryBooksServiceApplication.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PostCartsControllerTest {

private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	
	@BeforeEach
	public void setup() throws JSONException, IOException
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();	
	}
	
	//User CORRETTO
	private String JsonDataUser =  
				"{\r\n"
				+ "    \"userFirstName\": \"ProvaNome\",\r\n"
				+ "    \"userLastName\": \"ProvaCognome\",\r\n"
				+ "    \"username\": \"ProvaUsername\",\r\n"
				+ "    \"userEmail\": \"provaEmail\",\r\n"
				+ "    \"userPassword\": \"provaPassword\",\r\n"
				+ "    \"userRole\": \"USER\"\r\n"
				+ "}";
	
	//Book CORRETTO
	private String JsonDataWithCategorie =  
					"{\r\n"
					+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
					+ "    \"authorFname\": \"ProvaNome\",\r\n"
					+ "    \"authorLname\": \"ProvaCognome\",\r\n"
					+ "    \"releasedYear\": 1980,\r\n"
					+ "    \"stockQuantity\": 200,\r\n"
					+ "    \"pages\": 300,\r\n"
					+ "    \"price\": 100.50,\r\n"
					+ "    \"imgUrl\": \"ProvaCognome\",\r\n"
					+ "    \"booksCategory\": [\r\n"
					+ "        {\r\n"
					+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
					+ "        }\r\n"
					+ "    ]\r\n"
					+ "}";
	
	//Book CORRETTO
	private String JsonDataWithCategorie2 =  
					"{\r\n"
					+ "    \"bookTitle\": \"Unit Test Inserimento2\",\r\n"
					+ "    \"authorFname\": \"ProvaNome2\",\r\n"
					+ "    \"authorLname\": \"ProvaCognome2\",\r\n"
					+ "    \"releasedYear\": 1989,\r\n"
					+ "    \"stockQuantity\": 2080,\r\n"
					+ "    \"pages\": 3080,\r\n"
					+ "    \"price\": 100.50,\r\n"
					+ "    \"imgUrl\": \"ProvaCognome2\",\r\n"
					+ "    \"booksCategory\": [\r\n"
					+ "        {\r\n"
					+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
					+ "        }\r\n"
					+ "    ]\r\n"
					+ "}";
	
	//VERIFICA INSERIMENTO CORRETTO di 1 USER per TEST
	@Test
	@Order(1)
	public void testInsUserCorrect() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonDataUser)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId").exists())
				.andExpect(jsonPath("$.userFirstName").exists())
				.andExpect(jsonPath("$.userLastName").exists())
				.andExpect(jsonPath("$.username").exists())
				.andExpect(jsonPath("$.userEmail").exists())
				.andExpect(jsonPath("$.userPassword").exists())
				.andExpect(jsonPath("$.userRole").exists())
				.andExpect(jsonPath("$.cart").exists())
				.andExpect(jsonPath("$.cart.cartId").exists())
				.andExpect(jsonPath("$.cart.books").exists())
				.andExpect(jsonPath("$.lisOrdini").exists())
				.andDo(print());
			
	}
	
	//Inserisce un book di PROVA 1 per testrne i valori
	@Test
	@Order(2)
	public void testInsBookForTest() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataWithCategorie)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.bookId").exists())
						.andExpect(jsonPath("$.bookTitle").exists())
						.andExpect(jsonPath("$.authorFname").exists())
						.andExpect(jsonPath("$.authorLname").exists())
						.andExpect(jsonPath("$.releasedYear").exists())
						.andExpect(jsonPath("$.stockQuantity").exists())
						.andExpect(jsonPath("$.pages").exists())
						.andExpect(jsonPath("$.price").exists())
						.andExpect(jsonPath("$.booksCategory.[0]").exists())
						.andDo(print());
					
	}
	
	//Inserisce un book di PROVA 2 per testrne i valori
	@Test
	@Order(3)
	public void testInsBookForTest2() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataWithCategorie2)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isCreated())
						.andExpect(jsonPath("$.bookId").exists())
						.andExpect(jsonPath("$.bookTitle").exists())
						.andExpect(jsonPath("$.authorFname").exists())
						.andExpect(jsonPath("$.authorLname").exists())
						.andExpect(jsonPath("$.releasedYear").exists())
						.andExpect(jsonPath("$.stockQuantity").exists())
						.andExpect(jsonPath("$.pages").exists())
						.andExpect(jsonPath("$.price").exists())
						.andExpect(jsonPath("$.booksCategory.[0]").exists())
						.andDo(print());
						
	}
	
	
	//Inserisce un book in un CART BY USERNAME e BOOK TITLE
	@Test
	@Order(4)
	public void testInsBooInTheCart() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/carts/post/ProvaUsername/booksByTitle/Unit Test Inserimento")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataWithCategorie)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$.cartId").exists())
						.andExpect(jsonPath("$.books").exists())
						.andExpect(jsonPath("$.books.[0]bookId").exists())
						.andExpect(jsonPath("$.books.[0]bookTitle").exists())
						.andExpect(jsonPath("$.books.[0]bookTitle").value("Unit Test Inserimento"))
						.andExpect(jsonPath("$.books.[0]authorFname").exists())
						.andExpect(jsonPath("$.books.[0]authorFname").value("ProvaNome"))
						.andExpect(jsonPath("$.books.[0]authorLname").exists())
						.andExpect(jsonPath("$.books.[0]authorLname").value("ProvaCognome"))
						.andExpect(jsonPath("$.books.[0]releasedYear").exists())
						.andExpect(jsonPath("$.books.[0]releasedYear").value(1980))
						.andExpect(jsonPath("$.books.[0]stockQuantity").exists())
						.andExpect(jsonPath("$.books.[0]stockQuantity").value(200))
						.andExpect(jsonPath("$.books.[0]pages").exists())
						.andExpect(jsonPath("$.books.[0]pages").value(300))
						.andExpect(jsonPath("$.books.[0]price").exists())
						.andExpect(jsonPath("$.books.[0]price").value(100.50))
						.andExpect(jsonPath("$.books.[0]imgUrl").exists())
						.andExpect(jsonPath("$.books.[0]imgUrl").value("ProvaCognome"))
						.andExpect(jsonPath("$.books.[0]booksCategory").exists())
						.andExpect(jsonPath("$.books.[0]booksCategory.[0]categoriaTitle").exists())
						.andExpect(jsonPath("$.books.[0]booksCategory.[0]categoriaTitle").value("Romanzo"))
						.andDo(print());
	}
	
	//RIMUOVE un book n un CART BY USERNAME e BOOK TITLE
	@Test
	@Order(5)
	public void testRemoveBooInTheCart() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.delete("/api/carts/delete/ProvaUsername/booksByTitle/Unit Test Inserimento")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.cartId").exists())
						.andExpect(jsonPath("$.books").exists())
						.andExpect(jsonPath("$.books.[0]bookId").doesNotExist())
						.andExpect(jsonPath("$.books.[0]bookTitle").doesNotExist())
						.andExpect(jsonPath("$.books.[0]authorFname").doesNotExist())
						.andExpect(jsonPath("$.books.[0]authorLname").doesNotExist())
						.andExpect(jsonPath("$.books.[0]releasedYear").doesNotExist())
						.andExpect(jsonPath("$.books.[0]stockQuantity").doesNotExist())
						.andExpect(jsonPath("$.books.[0]pages").doesNotExist())
						.andExpect(jsonPath("$.books.[0]price").doesNotExist())
				
						.andExpect(jsonPath("$.books.[0]imgUrl").doesNotExist())
						.andExpect(jsonPath("$.books.[0]booksCategory").doesNotExist())
						.andExpect(jsonPath("$.books.[0]booksCategory.[0]categoriaTitle").doesNotExist())
						.andDo(print());	
	}
	
	//Elimina USER DI TEST
	@Test
	@Order(10)
	public void removeUserTest() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/byUsername/ProvaUsername")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andDo(print());
	}
	
	//Elimina il books 1 appena creato per TITLE
	@Test
	@Order(11)
	public void testDeleteBooksByTitle1() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/delete/byTitle/Unit Test Inserimento")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andDo(print());
	}
	
	//Elimina il books 2 appena creato per TITLE
	@Test
	@Order(12)
	public void testDeleteBooksByTitle2() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/delete/byTitle/Unit Test Inserimento2")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andDo(print());
	}
	
	//VERIFICA che il carrello venga cancellato quando anche user viene cancellato
	@Test
	@Order(13)
	public void testGetCartByUsername() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/carts/get/byUsername/ProvaUsername")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun cart per user con username: ProvaUsername"))
					.andDo(print());
	}
}

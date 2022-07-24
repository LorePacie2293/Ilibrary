package com.Ilibrary.BooksService.controllerGet.test;

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
public class GetCartsControllerTest {

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
	
	
	//VERIFICA la restituzione del CART di un user by USERNAME
	@Test
	@Order(2)
	public void testGetCartByUsername() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/carts/get/byUsername/ProvaUsername")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.cartId").exists())
				.andExpect(jsonPath("$.books").exists())
				.andDo(print());
	}
	
	//VERIFICA la restituzione del CART di un user by USERNAME
	@Test
	@Order(3)
	public void testGetCartByUsernameNotFound() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/carts/get/byUsername/ProvaUsernamegfdgfgfgf")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun cart per user con username: ProvaUsernamegfdgfgfgf"))
					.andDo(print());
	}
	
	//VERIFICA la restituzione DEI CARTS che contengono un BOOK
	@Test
	@Order(4)
	public void testGetCartByBookId() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/carts/get/byUsername/ProvaUsernamegfdgfgfgf")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun cart per user con username: ProvaUsernamegfdgfgfgf"))
					.andDo(print());
	}
	
	//Elimina il books appena creato per TITLE
	@Test
	@Order(10)
	public void removeDeleteTest() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/byUsername/ProvaUsername")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andDo(print());
	}
}

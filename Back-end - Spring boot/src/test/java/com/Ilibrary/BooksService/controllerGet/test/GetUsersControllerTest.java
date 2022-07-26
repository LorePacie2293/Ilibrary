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
public class GetUsersControllerTest {

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
		
	//verifica la richiesta di tutti gli USERS
	@Test
	@Order(2)
	public void testGeUsersByAll() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/get/byAll")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].userId").exists())
					.andExpect(jsonPath("$[0].userLastName").exists())
					.andExpect(jsonPath("$[0].username").exists())
					.andExpect(jsonPath("$[0].userEmail").exists())
					.andExpect(jsonPath("$[0].userPassword").exists())
					.andExpect(jsonPath("$[0].userRole").exists())
					.andExpect(jsonPath("$[0].cart").exists())
					.andExpect(jsonPath("$[0].lisOrdini").exists())
					.andDo(print());
	}
	
	//verifica la richiesta 1 user tramite USERNAME
	@Test
	@Order(3)
	public void testGetUserByUsername() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.get("/api/users/get/byUsername/ProvaUsername")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andExpect(jsonPath("$.userId").exists())
						.andExpect(jsonPath("$.userLastName").exists())
						.andExpect(jsonPath("$.username").exists())
						.andExpect(jsonPath("$.userEmail").exists())
						.andExpect(jsonPath("$.userPassword").exists())
						.andExpect(jsonPath("$.userRole").exists())
						.andExpect(jsonPath("$.cart").exists())
						.andExpect(jsonPath("$.lisOrdini").exists())
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

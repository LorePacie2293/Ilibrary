package com.Ilibrary.BooksService.controllerDelete.test;

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
public class DeleteUsersControllerTest {

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
					+ "    \"userPassword\": \"provaPass\",\r\n"
					+ "    \"userRole\": \"USER\"\r\n"
					+ "}";
	
	//Inserisce un USER di PROVA per testrne i valori
	@Test
	@Order(1)
	public void testInsUserForTest() throws Exception
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
	
	//Elimina lo user appena creato per USERNAME
	@Test
	@Order(2)
	public void testDeleteUsersByUsername() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/byUsername/ProvaUsername")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andDo(print());
	}
	
	//Elimina lo user appena creato per USERNAME
	@Test
	@Order(3)
	public void testDeleteUsersByUsernameNotFound() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/delete/byUsername/ProvaUsername")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound())
						.andDo(print());
	}
}

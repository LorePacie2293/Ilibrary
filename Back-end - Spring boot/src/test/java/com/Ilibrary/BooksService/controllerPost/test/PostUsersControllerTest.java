package com.Ilibrary.BooksService.controllerPost.test;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.Ilibrary.BooksService.Repository.BooksRepository;

@ContextConfiguration(classes = IlibraryBooksServiceApplication.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PostUsersControllerTest {

	private MockMvc mockMvc;
		
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private BooksRepository booksRepository;
	
	
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
	
	//VERIFICA INSERIMENTO CORRETTO di 1 USER
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
	
	//User NO FIRST NAME
	private String JsonDataUserNoFirstName =  
						"{\r\n"
						+ "    \"userLastName\": \"ProvaCognome\",\r\n"
						+ "    \"username\": \"ProvaUsername\",\r\n"
						+ "    \"userEmail\": \"provaEmail\",\r\n"
						+ "    \"userPassword\": \"provaPassword\",\r\n"
						+ "    \"userRole\": \"USER\"\r\n"
						+ "}";
		
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(2)
	public void testErrInsBookValidationNoFisrName() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JsonDataUserNoFirstName)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.codice").value(400))
					.andExpect(jsonPath("$.messaggio").value("FIRSTNAME non può essere NULL"))
					.andDo(print());
	}
	
	//User NO FIRST NAME
		private String JsonDataUserNoLastName =  
							"{\r\n"
							+ "    \"userFirstName\": \"ProvaNome\",\r\n"
							+ "    \"username\": \"ProvaUsername\",\r\n"
							+ "    \"userEmail\": \"provaEmail\",\r\n"
							+ "    \"userPassword\": \"provaPassword\",\r\n"
							+ "    \"userRole\": \"USER\"\r\n"
							+ "}";
			
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(3)
	public void testErrInsBookValidationNoLastName() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataUserNoLastName)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andExpect(jsonPath("$.codice").value(400))
						.andExpect(jsonPath("$.messaggio").value("LASTNAME non può essere NULL"))
						.andDo(print());
	}
	
	//User NO FIRST NAME
	private String JsonDataUserNoUsername =  
								"{\r\n"
								+ "    \"userFirstName\": \"ProvaNome\",\r\n"
								+ "    \"userLastName\": \"ProvaCognome\",\r\n"
								+ "    \"userEmail\": \"provaEmail\",\r\n"
								+ "    \"userPassword\": \"provaPassword\",\r\n"
								+ "    \"userRole\": \"USER\"\r\n"
								+ "}";
				
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(4)
	public void testErrInsBookValidationNoUsername() throws Exception
	{
					mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
							.contentType(MediaType.APPLICATION_JSON)
							.content(JsonDataUserNoUsername)
							.accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isBadRequest())
							.andExpect(jsonPath("$.codice").value(400))
							.andExpect(jsonPath("$.messaggio").value("USERNAME non può essere NULL"))
							.andDo(print());
	}
	
	//User NO FIRST NAME
	private String JsonDataUserNoEmail =  
								"{\r\n"
								+ "    \"userFirstName\": \"ProvaNome\",\r\n"
								+ "    \"userLastName\": \"ProvaCognome\",\r\n"
								+ "    \"username\": \"ProvaUsername\",\r\n"
								+ "    \"userPassword\": \"provaPassword\",\r\n"
								+ "    \"userRole\": \"USER\"\r\n"
								+ "}";
					
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(5)
	public void testErrInsBookValidationNoEmail() throws Exception
	{
						mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
								.contentType(MediaType.APPLICATION_JSON)
								.content(JsonDataUserNoEmail)
								.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isBadRequest())
								.andExpect(jsonPath("$.codice").value(400))
								.andExpect(jsonPath("$.messaggio").value("EMAIL non può essere NULL"))
								.andDo(print());
	}
	
	//User NO FIRST NAME
	private String JsonDataUserNoPassword =  
								"{\r\n"
								+ "    \"userFirstName\": \"ProvaNome\",\r\n"
								+ "    \"userLastName\": \"ProvaCognome\",\r\n"
								+ "    \"username\": \"ProvaUsername\",\r\n"
								+ "    \"userEmail\": \"provaEmail\",\r\n"
								+ "    \"userRole\": \"USER\"\r\n"
								+ "}";
						
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(6)
	public void testErrInsBookValidationNoPassword() throws Exception
	{
					mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
							.contentType(MediaType.APPLICATION_JSON)
							.content(JsonDataUserNoPassword)
							.accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isBadRequest())
							.andExpect(jsonPath("$.codice").value(400))
							.andExpect(jsonPath("$.messaggio").value("PASSWORD non può essere NULL"))
							.andDo(print());
	}
	
	//VERIFICA INSERIMENTO CORRETTO di 1 USER
	@Test
	@Order(7)
	public void testInsUserAlredyInsert() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/post")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JsonDataUser)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotAcceptable())
					.andExpect(jsonPath("$.codice").value(406))
					.andExpect(jsonPath("$.messaggio").value("Username ProvaUsername e gia presente nel database"))
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

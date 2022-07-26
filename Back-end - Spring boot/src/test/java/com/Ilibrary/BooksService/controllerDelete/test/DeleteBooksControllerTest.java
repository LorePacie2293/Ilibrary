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
public class DeleteBooksControllerTest {

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
	
	
	//Book ORIGINALE creato per test, ed in seguito eliminato
	private String JsonData =  
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
	
	//Inserisce un book di PROVA per testrne i valori
	@Test
	@Order(1)
	public void testInsBookForTest() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonData)
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
	
	//Elimina il books appena creato per TITLE
	@Test
	@Order(2)
	public void testDeleteBooksByTitle() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/delete/byTitle/Unit Test Inserimento")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andDo(print());
	}
	
	//verifica la richiesta di un book INESISTENTE
	@Test
	@Order(3)
	public void testDeleteBooksByTitleError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/delete/byTitle/Unit Test Inserimento")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isNotFound())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andDo(print());
	}
	
}

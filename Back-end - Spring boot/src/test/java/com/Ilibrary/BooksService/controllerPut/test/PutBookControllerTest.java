package com.Ilibrary.BooksService.controllerPut.test;

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
public class PutBookControllerTest {


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
	
	//Book ORIGINALE
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
	
	//BOOK MODIFICATO
	private String JsonDataUpdate =  
			"{\r\n"
			+ "    \"bookTitle\": \"Modifica 1\",\r\n"
			+ "    \"authorFname\": \"dffgfgfg\",\r\n"
			+ "    \"authorLname\": \"fgfgfgf\",\r\n"
			+ "    \"releasedYear\": 196580,\r\n"
			+ "    \"stockQuantity\": 20650,\r\n"
			+ "    \"pages\": 30650,\r\n"
			+ "    \"price\": 500.50,\r\n"
			+ "    \"imgUrl\": \"gtgtgtg\",\r\n"
			+ "    \"booksCategory\": [\r\n"
			+ "        {\r\n"
			+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "            \"categoriaTitle\": \"Fantasy\"\r\n"
			+ "        }\r\n"
			+ "    ]\r\n"
			+ "}";
	
	//Test verifica che TUTTI I CAMPI vengano modificati
	@Test
	@Order(1)
	public void testUpdateBook() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byTitle/Il signore degli anelli")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].bookId").exists())
				.andExpect(jsonPath("$[0].bookTitle").exists())
				.andExpect(jsonPath("$[0].authorFname").exists())
				.andExpect(jsonPath("$[0].authorLname").exists())
				.andExpect(jsonPath("$[0].releasedYear").exists())
				.andExpect(jsonPath("$[0].stockQuantity").exists())
				.andExpect(jsonPath("$[0].pages").exists())
				.andExpect(jsonPath("$[0].price").exists())
				.andExpect(jsonPath("$[0].booksCategory").exists())
				.andDo(print());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/books/put")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonDataUpdate)
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
}

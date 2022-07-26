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
public class PostBookControllerTest {


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
	
	//VERIFICA INSERIMENTO CORRETTO con 1 Categoria
	@Test
	@Order(1)
	public void testInsBookCorrect() throws Exception
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
	
	//Book NO TITLE
	private String JsonDataNoTitle =  
				"{\r\n"
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
	
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(2)
	public void testErrInsBookValidationNoTitle() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonDataNoTitle)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codice").value(400))
				.andExpect(jsonPath("$.messaggio").value("Titolo non può essere NOT NULL"))
				.andDo(print());
	}
	
	//Book NO FISRT NAME
	private String JsonDataNoFirstname =  
					"{\r\n"
					+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
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
		
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(3)
	public void testErrInsBookValidationNoauthorFname() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonDataNoFirstname)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.codice").value(400))
				.andExpect(jsonPath("$.messaggio").value("firstname non può esere NOT NULL"))
				.andDo(print());
	}
	
	//Book NO LAST NAME
	private String JsonDataNoLastname =  
						"{\r\n"
						+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
						+ "    \"authorFname\": \"ProvaCognome\",\r\n"
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
			
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(4)
	public void testErrInsBookValidationNoauthorLname() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JsonDataNoLastname)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.codice").value(400))
					.andExpect(jsonPath("$.messaggio").value("last name non può essere not null"))
					.andDo(print());
	}
	
	//Book NO YEAR
	private String JsonDataNoYear =  
							"{\r\n"
							+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
							+ "    \"authorFname\": \"ProvaCognome\",\r\n"
							+ "    \"authorLname\": \"ProvaCognome\",\r\n"
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
				
	@Test
	@Order(5)
	public void testErrInsBookValidationNoYear() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
					.contentType(MediaType.APPLICATION_JSON)
					.content(JsonDataNoYear)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.codice").value(400))
					.andExpect(jsonPath("$.messaggio").value("release year deve avere MINIMO 1"))
					.andDo(print());
	}
	
	//Book NO STOCK QUANTITY
	private String JsonDataNoStockQuantity =  
								"{\r\n"
								+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
								+ "    \"authorFname\": \"ProvaCognome\",\r\n"
								+ "    \"authorLname\": \"ProvaCognome\",\r\n"
								+ "    \"releasedYear\": 1980,\r\n"
								+ "    \"pages\": 300,\r\n"
								+ "    \"price\": 100.50,\r\n"
								+ "    \"imgUrl\": \"ProvaCognome\",\r\n"
								+ "    \"booksCategory\": [\r\n"
								+ "        {\r\n"
								+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
								+ "        }\r\n"
								+ "    ]\r\n"
								+ "}";
					
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(6)
	public void testErrInsBookValidationStockQuantity() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataNoStockQuantity)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andExpect(jsonPath("$.codice").value(400))
						.andExpect(jsonPath("$.messaggio").value("stock quantity deve avere MINIMO 1"))
						.andDo(print());
	}
	
	//Book NO PAGES
	private String JsonDataNoPages =  
									"{\r\n"
									+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
									+ "    \"authorFname\": \"ProvaCognome\",\r\n"
									+ "    \"authorLname\": \"ProvaCognome\",\r\n"
									+ "    \"releasedYear\": 1980,\r\n"
									+ "    \"stockQuantity\": 200,\r\n"
									+ "    \"price\": 100.50,\r\n"
									+ "    \"imgUrl\": \"ProvaCognome\",\r\n"
									+ "    \"booksCategory\": [\r\n"
									+ "        {\r\n"
									+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
									+ "        }\r\n"
									+ "    ]\r\n"
									+ "}";
						
	//VERIFICHE BINDING VALIDATION 
	@Test
	@Order(7)
	public void testErrInsBookValidationPages() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
							.contentType(MediaType.APPLICATION_JSON)
							.content(JsonDataNoPages)
							.accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isBadRequest())
							.andExpect(jsonPath("$.codice").value(400))
							.andExpect(jsonPath("$.messaggio").value("pages deve avere MINIMO 1"))
							.andDo(print());
	}
	
	//Book NO IMAGE URL
	private String JsonDataNoImageUrl =  
									"{\r\n"
									+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
									+ "    \"authorFname\": \"ProvaCognome\",\r\n"
									+ "    \"authorLname\": \"ProvaCognome\",\r\n"
									+ "    \"releasedYear\": 1980,\r\n"
									+ "    \"stockQuantity\": 200,\r\n"
									+ "    \"pages\": 300,\r\n"
									+ "    \"price\": 100.50,\r\n"
									+ "    \"booksCategory\": [\r\n"
									+ "        {\r\n"
									+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
									+ "        }\r\n"
									+ "    ]\r\n"
									+ "}";
							
	//VRIFICHE BINDING VALIDATION 
	@Test
	@Order(8)
	public void testErrInsBookValidationCategory() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
							.contentType(MediaType.APPLICATION_JSON)
							.content(JsonDataNoImageUrl)
							.accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isBadRequest())
							.andExpect(jsonPath("$.codice").value(400))
							.andExpect(jsonPath("$.messaggio").value("imgUrl non può essere null"))
							.andDo(print());
	}
	
	//Book NO CATEGORY URL
	private String JsonDataNoCategory =  
									"{\r\n"
									+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
									+ "    \"authorFname\": \"ProvaCognome\",\r\n"
									+ "    \"authorLname\": \"ProvaCognome\",\r\n"
									+ "    \"releasedYear\": 1980,\r\n"
									+ "    \"stockQuantity\": 200,\r\n"
									+ "    \"pages\": 300,\r\n"
									+ "    \"price\": 100.50,\r\n"
									+ "    \"imgUrl\": \"ProvaCognome\"\r\n"
									+ "}";
								
	//VRIFICHE BINDING VALIDATION 
	@Test
	@Order(9)
	public void testErrInsBookValidationImageUrl() throws Exception
	{
					mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
								.contentType(MediaType.APPLICATION_JSON)
								.content(JsonDataNoCategory)
								.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isBadRequest())
								.andExpect(jsonPath("$.codice").value(400))
								.andExpect(jsonPath("$.messaggio").value("category deve avere MINIMO 1"))
								.andDo(print());
	}
	
	//Verifica quando si inserisce una categoria NON ESISTENTE
	//Book CORRETTO
	private String JsonDataWithCategorieErr =  
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
				+ "            \"categoriaTitle\": \"RomTGFDGFDVanzo\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
	
	@Test
	@Order(9)
	public void testErrInsBookCategory() throws Exception
	{
					mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
								.contentType(MediaType.APPLICATION_JSON)
								.content(JsonDataWithCategorieErr)
								.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isNotFound())
								.andExpect(jsonPath("$.codice").value(404))
								.andExpect(jsonPath("$.messaggio").value("Nessuna categoria inserita nel db"))
								.andDo(print());
	}
	
	//Book NO CATEGORY URL
	private String JsonDataNoPrice =  
									"{\r\n"
									+ "    \"bookTitle\": \"Unit Test Inserimento\",\r\n"
									+ "    \"authorFname\": \"ProvaCognome\",\r\n"
									+ "    \"authorLname\": \"ProvaCognome\",\r\n"
									+ "    \"releasedYear\": 1980,\r\n"
									+ "    \"stockQuantity\": 200,\r\n"
									+ "    \"pages\": 300,\r\n"
									+ "    \"imgUrl\": \"ProvaCognome\"\r\n"
									+ "    \"booksCategory\": [\r\n"
									+ "        {\r\n"
									+ "            \"categoriaTitle\": \"Romanzo\"\r\n"
									+ "        }\r\n"
									+ "    ]\r\n"
									+ "}";
								
	//VRIFICHE BINDING VALIDATION 
	@Test
	@Order(10)
	public void testErrInsBookValidationNoPrice() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.post("/api/books/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(JsonDataNoPrice)
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isBadRequest())
						.andDo(print());
	}
	
	//Elimina il books appena creato per TITLE
	@Test
	@Order(15)
	public void removeDeleteTest() throws Exception
	{
				mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/delete/byTitle/Unit Test Inserimento")
							.accept(MediaType.APPLICATION_JSON))
							.andExpect(status().isOk())
							.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
							.andDo(print());
	}
}

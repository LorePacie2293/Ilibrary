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
public class GetBooksControllerTest {

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
		
	//verifica la richiesta di tutti i BOOKS
	@Test
	@Order(1)
	public void testGetBooksAll() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byAll")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].bookId").exists())
					.andDo(print());
	}
	
	//Inserisce un book di PROVA per testrne i valori
	@Test
	@Order(2)
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
		
	//verifica BOOK restituito con TITLE Il signore degli anelli
	@Test
	@Order(4)
	public void testGetBooksByTitle() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byTitle/Unit Test Inserimento")
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
	}
	
	//verifica BOOK byTitle INSESISTENTE
	@Test
	@Order(5)
	public void testGetBooksByTitleError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byTitle/Idfdfddf")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun book con title: Idfdfddf"))
					.andDo(print());
	}
	
	//verifica BOOKS restituito con AUTHOR FIRST NAME
	@Test
	@Order(6)
	public void testGetBooksByAuthFname() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byAuthFname/ProvaNome")
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
	}
	
	//verifica BOOKS author first name INSESISTENTE
	@Test
	@Order(7)
	public void testGetBooksByAuthFisrtNameError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byAuthFname/Idfdfddf")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun book con author first name: Idfdfddf"))
					.andDo(print());
	}
	
	//verifica BOOKS restituito con AUTHOR LAST NAME
	@Test
	@Order(8)
	public void testGetBooksByAuthLname() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byAuthLname/ProvaCognome")
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
	}
	
	//verifica BOOKS restituito con AUTHOR LAST NAME
	@Test
	@Order(9)
	public void testGetBooksByAuthLnameError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byAuthLname/sasasasd")
				.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun book con author last name: sasasasd"))
					.andDo(print());		
	}
	
	//verifica BOOKS restituito con YEAR
	@Test
	@Order(10)
	public void testGetBooksByYear() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byYear/1980")
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
	}
	
	//verifica BOOKS restituito con YEAR INESISTENTE
	@Test
	@Order(11)
	public void testGetBooksByYearError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byYear/322324")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.messaggio").exists())
				.andExpect(jsonPath("$.messaggio").value("Nessun book con year released: 322324"))
				.andDo(print());		
	}
	
	//verifica BOOKS restituito con STOCK QUANTITY
	@Test
	@Order(10)
	public void testGetBooksByStockQuantity() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byStockQuantity/200")
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
	}
	
	//verifica BOOKS restituito con YEAR INESISTENTE
	@Test
	@Order(11)
	public void testGetBooksByStockQuantityError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byStockQuantity/322324")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.messaggio").exists())
				.andExpect(jsonPath("$.messaggio").value("Nessun book con stock quantity: 322324"))
				.andDo(print());		
	}
	
	//verifica BOOKS restituito con PAGES
	@Test
	@Order(12)
	public void testGetBooksByPages() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byPages/300")
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
	}
		
	//verifica BOOKS restituito con PAGES INESISTENTE
	@Test
	@Order(13)
	public void testGetBooksByPagesError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byPages/322324")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.messaggio").exists())
				.andExpect(jsonPath("$.messaggio").value("Nessun book con pages: 322324"))
				.andDo(print());		
	}
	
	
	//verifica BOOKS restituito con PAGES
	@Test
	@Order(14)
	public void testGetBooksByPrice() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byPrice/100.50")
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
	}
			
	//verifica BOOKS restituito con PAGES INESISTENTE
	@Test
	@Order(15)
	public void testGetBooksByPriceError() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/get/byPrice/322324")
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.messaggio").exists())
					.andExpect(jsonPath("$.messaggio").value("Nessun book con price: 322324.0"))
					.andDo(print());		
	}
		
	//Elimina il books appena creato per TITLE
	@Test
	@Order(16)
	public void removeDeleteTest() throws Exception
	{
			mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/delete/byTitle/Unit Test Inserimento")
						.accept(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
						.andDo(print());
	}
}

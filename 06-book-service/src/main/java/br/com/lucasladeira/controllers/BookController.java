package br.com.lucasladeira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasladeira.entities.Book;
import br.com.lucasladeira.proxy.CambioProxy;
import br.com.lucasladeira.repositories.BookRepository;

@RestController
@RequestMapping("/book-service")
public class BookController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CambioProxy cambioProxy;
	
	@GetMapping(value = "/{id}/{currency}")
	public Book getBookById(@PathVariable("id") Long id,
							@PathVariable("currency") String currency) {
		
		var book = bookRepository.getById(id);
		if (book==null) throw new RuntimeException("Book not Found");
		
		var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);
		
		var port = environment.getProperty("local.server.port");
		book.setEnvironment(
				"Book Port: " + port + 
				" Cambio Port: " + cambio.getEnvironment());
		
		book.setPrice(cambio.getConvertedValue());
		
		return book;
	}
	/**	
	@GetMapping(value = "/{id}/{currency}")
	public Book getBookById(@PathVariable("id") Long id,
							@PathVariable("currency") String currency) {
		
		var book = bookRepository.getById(id);
		if (book==null) throw new RuntimeException("Book not Found");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		
		var response = new RestTemplate() //consumindo a api de cambio
					.getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}", Cambio.class, params);
		
		var cambio = response.getBody();
		
		var port = environment.getProperty("local.server.port");
		
		book.setEnvironment(port);
		book.setPrice(cambio.getConvertedValue());
		
		return book;
	}	
	**/
	
}

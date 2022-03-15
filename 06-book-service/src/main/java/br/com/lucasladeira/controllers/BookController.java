package br.com.lucasladeira.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasladeira.entities.Book;
import br.com.lucasladeira.repositories.BookRepository;

@RestController
@RequestMapping("/book-service")
public class BookController {

	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	@GetMapping(value = "/{id}/{currency}")
	public Book getBookById(@PathVariable("id") Long id,
							@PathVariable("currency") String currency) {
		
		var book = bookRepository.getById(id);
		if (book==null) throw new RuntimeException("Book not Found");
		
		var port = environment.getProperty("local.server.port");
		
		book.setEnvironment(port);
		
		return book;
	}
}

package br.com.lucasladeira.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.lucasladeira.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}

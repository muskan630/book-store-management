package com.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bRepo;
   
	// Save book
	public void save(Book b) {
		bRepo.save(b);
	}

	// Get all books
	public List<Book> getAllBook() {
		return bRepo.findAll();
	}

	// Get book by ID
	public Book getBookById(int id) {
	    System.out.println("Fetching book with ID: " + id);
	    return bRepo.findById(id).orElse(null);  // Returns null if book is not found
	}

	// Delete book by ID
	public void deleteById(int id) {
		bRepo.deleteById(id);
	}

	// Save or update book (fixed)
	public void saveBook(Book book) {
		bRepo.save(book);
	}
	public void deleteBook(int id) {
		bRepo.deleteById(id);
    }
	
}

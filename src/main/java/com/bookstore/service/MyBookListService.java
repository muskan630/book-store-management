package com.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.entity.MyBookList;
import com.bookstore.repository.MyBookRepository;

@Service
public class MyBookListService {
	@Autowired
	private MyBookRepository mybookRepository;

	// Save a book to MyBookList
	public void saveMyBook(MyBookList book) {
		 System.out.println("Saving to DB: " + book.getName() + ", Price: " + book.getPrice());
		mybookRepository.save(book);
	}


    // ✅ Method to get a book by ID (Fixes your issue)
    public MyBookList getBookById(int id) {
        return mybookRepository.findById(id).orElse(null);
    }

    // ✅ Method to delete a book from MyBookList
    public void deleteMyBook(int id) {
        mybookRepository.deleteById(id);
    }

    // ✅ Method to get all books from MyBookList
    public List<MyBookList> getAllMyBooks() {
        return mybookRepository.findAll();
    }


	public boolean existsById(int id) {
		 return mybookRepository.existsById(id);
	}

}

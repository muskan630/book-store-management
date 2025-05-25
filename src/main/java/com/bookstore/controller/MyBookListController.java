package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bookstore.entity.MyBookList;
import com.bookstore.service.MyBookListService;

@Controller
public class MyBookListController {

    @Autowired
    private MyBookListService service;

    @GetMapping("/my_books_list")
    public String viewMyBooks(Model model) {
        model.addAttribute("myBooks", service.getAllMyBooks());
        return "my-books";
    }

    @RequestMapping("/deleteMyList/{id}")
    public String deleteMyList(@PathVariable("id") int id) {
        if (service.getBookById(id) == null) {
            throw new RuntimeException("Cannot delete: Book not found!");
        }
        service.deleteMyBook(id);
        
        return "redirect:/my_books";  // ✅ Redirect correctly after deletion
    }
    
    @GetMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        MyBookList b = service.getBookById(id);
        
        if (b == null) {
            throw new RuntimeException("Book not found with id: " + id);  // Debugging
        }
        
        MyBookList mb = new MyBookList();
        mb.setId(b.getId());  // Ensure ID is set correctly
        mb.setName(b.getName());
        mb.setAuthor(b.getAuthor());
        mb.setPrice(b.getPrice());  // Ensure price is included if needed

        service.saveMyBook(mb);
        
        return "redirect:/my_books";  // ✅ Ensure correct redirection
    }
}
package com.bookstore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bookstore.entity.Book;
import com.bookstore.entity.MyBookList;
import com.bookstore.service.BookService;
import com.bookstore.service.MyBookListService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookController {
    
    @Autowired
    private BookService service;
    
    @Autowired
    private MyBookListService myBookService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";  // Redirect if user is not logged in
        }
        return "home";  // Show home page
    }

    @GetMapping("/book_register")
    public String bookRegister(Model model) {
    	model.addAttribute("book", new Book());  
        return "bookRegister";
    }

    @GetMapping("/available_books")
    public ModelAndView getAllBook() {
        List<Book> list = service.getAllBook();
        return new ModelAndView("bookList", "book", list);
    }

    @PostMapping("/save")public String saveBook(@ModelAttribute Book book) {
    	service.save(book);
        return "redirect:/available_books";  // ✅ Redirect correctly after saving
    }

    @GetMapping("/my_books")
    public String getMyBooks(Model model) {
        List<MyBookList> list = myBookService.getAllMyBooks();
        model.addAttribute("book", list);
        return "myBooks";
    }
    
    @PostMapping("/addToMyBooks/{id}")
    public String addToMyBooks(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Book book = service.getBookById(id);
        
        if (book == null) {
            redirectAttributes.addFlashAttribute("error", "Book not found!");
            return "redirect:/available_books";  // Redirect back if book doesn't exist
        }
        

        MyBookList myBook = new MyBookList(book.getName(), book.getAuthor(), book.getPrice());
        myBookService.saveMyBook(myBook);
        return "redirect:/my_books";
    }

    @GetMapping("/addToMyList/{id}")
    public String addToMyList(@PathVariable("id") int id) {
        Book b = service.getBookById(id);

        if (b == null) {
            System.out.println("Book not found with id: " + id);
            return "redirect:/available_books";  // Prevent error if book doesn't exist
        }

        MyBookList mb = new MyBookList();
        mb.setName(b.getName());
        mb.setAuthor(b.getAuthor());

        myBookService.saveMyBook(mb);
        return "redirect:/my_books";
    }
    @GetMapping("/editBook/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        Book b = service.getBookById(id);
        model.addAttribute("book", b);
        return "bookEdit";
    }

    @PostMapping("/updateBook/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") Book book) {
        book.setId(id);
        service.save(book);  
        return "redirect:/available_books";
    }

    @RequestMapping("/deleteBook/{id}")  
    public String deleteBook(@PathVariable("id") int id) {
        service.deleteBook(id);
        return "redirect:/available_books";
    }
}
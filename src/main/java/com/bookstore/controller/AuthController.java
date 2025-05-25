package com.bookstore.controller;

import com.bookstore.entity.User;
import com.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")  // Change mapping from "/" to "/login"
    public String showLoginPage() {
        return "login";  // Make sure login.html exists in templates
    }

    // Show Signup Page
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";  // Renders signup.html
    }

    // Handle User Signup
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        try {
            userService.registerUser(username, email, password);
            model.addAttribute("success", "Registration successful! You can now log in.");
            return "login"; // Redirect to login page after successful registration
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "signup"; // Stay on signup page if registration fails
        }
    }

    // Handle User Login
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.loginUser(username, password);
        if (user != null && user.getPassword().equals(password)) { 
            session.setAttribute("loggedInUser", user);  // Store user in session
            return "redirect:/home";  // Redirect to home page
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";  // Stay on login page with error message
        }
    }

    // Logout User
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Clear session
        return "redirect:/login";
    }

    // Show Home Page after Login
    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";  // Renders home.html where books are displayed
    }
}

package com.example.tvshow.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tvshow.models.State;
import com.example.tvshow.models.User;
import com.example.tvshow.service.SerUser;
import com.example.tvshow.valdation.UserValidator;

@Controller
public class UserCon {
	@Autowired
    private SerUser uService;
    @Autowired
    private UserValidator validator;

    @GetMapping("/")
    public String Index(@ModelAttribute("registration") User user, Model model, HttpSession session) {
        if(session.getAttribute("userId") != null)
            return "redirect:/shows";
        model.addAttribute("states", State.States);
        return "/users/index.jsp";
    }

    @PostMapping("/")
    public String Register(@Valid @ModelAttribute("registration") User user, BindingResult result, HttpSession session) {
        if(session.getAttribute("userId") != null)
            return "redirect:/shows";
        validator.validate(user, result);
        if(result.hasErrors())
            return "/users/index.jsp";
        User newUser = this.uService.registerUser(user);
        session.setAttribute("userId", newUser.getId());
        return "redirect:/shows";
    }

    @PostMapping("/login")
    public String Login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirs) {
        if(this.uService.authenticateUser(email, password)) {
        	User user = this.uService.getUserByEmail(email);
            session.setAttribute("userId", user.getId());
            return "redirect:/shows";
        }
        redirs.addFlashAttribute("error", "Invalid Email/Password");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if(session.getAttribute("userId") != null)
            session.invalidate();
        return "redirect:/";
    }
}

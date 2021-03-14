package com.example.tvshow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tvshow.models.*;
import com.example.tvshow.models.Show;
import com.example.tvshow.models.State;
import com.example.tvshow.models.User;
import com.example.tvshow.repositories.RateRepo;
import com.example.tvshow.repositories.ShowRepo;
import com.example.tvshow.repositories.UserRepo;
import com.example.tvshow.service.SerApp;
import com.example.tvshow.service.SerUser;
import com.example.tvshow.valdation.UserValidator;

@Controller
@RequestMapping("/shows")
public class ShowCon {
	private final RateRepo raterepo;
	private final ShowRepo showRep;
	private final SerApp serapp;
	private final SerUser seruser;
	
	public ShowCon(ShowRepo showRep,SerApp serapp,SerUser seruser,RateRepo raterepo) {
        this.showRep = showRep;
        this.serapp = serapp;
        this.seruser=seruser;
        this.raterepo=raterepo;
	}
	

	@RequestMapping("")
	public String home(Model model, HttpSession session) {
		if(session.getAttribute("userId") != null) {
			User user=seruser.findById((Long) session.getAttribute("userId"));
			model.addAttribute("user", user);
			List<Show> shows = showRep.findAll();
			model.addAttribute("shows", shows);
			return "show/dashboard.jsp";
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/new")
	public String showCreate(@ModelAttribute("show") Show show,Model model, HttpSession session) {
		if(session.getAttribute("userId") != null) {
			User user=seruser.findById((Long) session.getAttribute("userId"));
			model.addAttribute("states", State.States);
			
			return "/show/Create.jsp";
		}
		return "redirect:/";
	}
	
	@PostMapping("/new")
	public String createshow(@Valid @ModelAttribute("show") Show show, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			return "redirect:/shows";
		} 
		User user=seruser.findById((Long) session.getAttribute("userId"));
		seruser.addshowuser(user, show);
		serapp.createshow(show);
		return "/show/Create.jsp";
		
	}
//	
	@RequestMapping("/{show_id}")
	public String showTask(Model model, HttpSession session, @PathVariable("show_id") Long id,@ModelAttribute("rate") Rate rate) {
		if(session.getAttribute("userId") != null) {
			User user=seruser.findById((Long) session.getAttribute("userId"));
			Show show = serapp.findShowById(id);
			serapp.calavg(show);
			model.addAttribute("show", show);
			model.addAttribute("rates", show.getRates());
			return "show/show.jsp";
		}
		return "redirect:/shows";
	}
//	
	@RequestMapping(value="/shows/{show_id}",method=RequestMethod.POST)
	public String showafter(Model model,@Valid @ModelAttribute("rate") Rate rate,BindingResult result, HttpSession session, @PathVariable("show_id") Long id) {
		if(result.hasErrors()) {
			return "redirect:/shows/{show_id}";
		} 
		User user=seruser.findById((Long) session.getAttribute("userId"));
		Show show = serapp.findShowById(id);
		Rate r=rate;
		r.setUser(user);
		r.setShow(show);
		raterepo.save(r);
		serapp.calavg(show);
		model.addAttribute("rates", show.getRates());
		model.addAttribute("show", show);
		return "redirect:/shows/{show_id}";
	}
//	
	@RequestMapping("{show_id}/edit")
	public String edit(@PathVariable("show_id") Long id,@ModelAttribute("show") Show show, BindingResult result, HttpSession session, Model model) {
		Show show1= serapp.findShowById(id);
		model.addAttribute("states", State.States);
		model.addAttribute("show",show1);
		return "show/edit.jsp";
	}
	
	@RequestMapping(value="/{show_id}/edit",method=RequestMethod.POST)
	public String editTask(@PathVariable("show_id") Long id,@Valid @ModelAttribute("show") Show show, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			return "redirect:/shows/{show_id}/edit";
		} 
		Show show1= serapp.findShowById(id);
		serapp.update(show1, show);
		return "redirect:/shows/{show_id}";
	}
//	
//	@GetMapping("/edit/show/{show_id}/complete")
//	public String completeTask(@PathVariable("show_id") Long id) {
//		Show show = showServ.findShowById(id);
//		show.setCompleted(1);
//		showRep.save(show);
//		return "redirect:/shows";
//		
//	}
//	
	@RequestMapping("/{show_id}/delete")
	public String deleteShow(@PathVariable("show_id") Long id) {
		showRep.deleteById(id);
		return "redirect:/shows";
	}
}

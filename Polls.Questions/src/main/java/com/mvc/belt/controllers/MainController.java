package com.mvc.belt.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mvc.belt.models.Task;
import com.mvc.belt.models.User;
import com.mvc.belt.services.TaskService;
import com.mvc.belt.services.UserService;
import com.mvc.belt.validator.UserValidator;

@Controller
public class MainController {
	private final UserService userService;
	private final UserValidator userValidator;
	private final TaskService taskService;

	public MainController(UserService userService, UserValidator userValidator, TaskService taskService) {
		this.userService = userService;
		this.userValidator = userValidator;
		this.taskService = taskService;
	}

	// login page
	@RequestMapping("/")
	public String first(@ModelAttribute("user") User user) {
		return "login.jsp";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "login.jsp";
		}
		User u = userService.registerUser(user);
		session.setAttribute("userId", u.getId());
		return "redirect:/tasks";
	}

	
	@PostMapping("/login")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirs) {
		if(this.userService.authenticateUser(email, password)) {
			User user = this.userService.findByEmail(email);
			session.setAttribute("userId", user.getId());
			return "redirect:/tasks";
		}
		redirs.addFlashAttribute("error", "Invalid Email/Password");
		return "redirect:/";
	}
	



	@RequestMapping("/tasks")
	public String landing(HttpSession session, Model model) {

		if (session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);
			model.addAttribute("user", u);

			List<Task> tasklist = taskService.getAll();
			model.addAttribute("tasks", tasklist);
			return "landing.jsp";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/tasks/new")
	public String displayTask(@ModelAttribute("task") Task myTask, Model model, HttpSession session) {
		// if current user is in session then proceed, if not redirect to login page
		if (session.getAttribute("userId") != null) {
			// find a list of all users, it will be used in Dropdown menu
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);

			// get current user login info

			// when get user id from session, don't forget to cast data type to Long
			Long userId = (Long) session.getAttribute("userId");
			User u = userService.findUserById(userId);
			model.addAttribute("currentUser", u);
			return "task.jsp";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "/tasks/new", method = RequestMethod.POST)
	public String newTask(@Valid @ModelAttribute("task") Task myTask, BindingResult result, HttpSession session,
			Model model, RedirectAttributes limitError) {
		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("user", u);

		if (result.hasErrors()) {


			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			return "task.jsp";
		} else {
			
		
			// get assignee id
			Long aId = (Long) myTask.getAssignee().getId();
			User myAssignee = userService.findUserById(aId);
			List<Task> myList = myAssignee.getAssigned_tasks();
			
			if (myList.size() >= 3) {
				System.out.print("At limit");
				List<User> allusers = userService.findAll();
				model.addAttribute("users", allusers);
				limitError.addFlashAttribute("errors", "User cannot be assigned more than 4 tasks");
				return "redirect:/tasks/new";
				
				
			} else {

				Task newTask = taskService.createTask(myTask);

				newTask.setCreator(u);
				taskService.updateTask(newTask);

				return "redirect:/tasks";
			}
		}
	}

	@RequestMapping("/tasks/{id}")
	public String displayoneTask(Model model, HttpSession session, @PathVariable("id") Long taskId) {
		Task thisTask = taskService.findTask(taskId);
		model.addAttribute("task", thisTask);

		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);
		model.addAttribute("currentUserId", u.getId());
		return "showtask.jsp";
	}

	// show a task edit page - only creator can see (black belt requirement)
	@RequestMapping("/tasks/{id}/edit")
	public String Edittask(Model model, @ModelAttribute("edittask") Task myTask, @PathVariable("id") Long taskId,
			HttpSession session) {

	

		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);

		Task editingTask = taskService.findTask(taskId);


		if (u.getId() == editingTask.getCreator().getId()) {

			List<User> allUsers = userService.findAll();

			model.addAttribute("creator", editingTask.getCreator());
			model.addAttribute("edittask", editingTask);
			model.addAttribute("users", allUsers);
			model.addAttribute("id", editingTask.getId());
			return "edittask.jsp";
		} else {
			return "redirect:/tasks";
		}

	}

	@RequestMapping(value = "/tasks/{id}/edit", method = RequestMethod.POST)
	public String updateTask(Model model, @Valid @ModelAttribute("edittask") Task myTask, BindingResult result,
			@PathVariable("id") Long taskId) {

		if (result.hasErrors()) {
			List<User> allusers = userService.findAll();
			model.addAttribute("users", allusers);
			return "edittask.jsp";
		} else {
			taskService.createTask(myTask);
			return "redirect:/tasks";
		}
	}

	@RequestMapping("/tasks/{id}/delete")
	public String deleteTask(@PathVariable("id") Long myId) {
		Task deltask = taskService.findTask(myId);
		if (deltask != null) {
			taskService.deleteTask(myId);
			return "redirect:/tasks";
		} else {
			return "redirect:/tasks";
		}
	}

	@RequestMapping("/lowhigh")
	public String lowHigh(HttpSession session, Model model) {

		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);

		model.addAttribute("user", u);

		List<Task> tasklist = taskService.lowToHigh();
		model.addAttribute("tasks", tasklist);
		return "landing.jsp";
	}

	@RequestMapping("/highlow")
	public String HighLow(HttpSession session, Model model) {

		Long userId = (Long) session.getAttribute("userId");
		User u = userService.findUserById(userId);

		model.addAttribute("user", u);

		List<Task> tasklist = taskService.highToLow();
		model.addAttribute("tasks", tasklist);
		return "landing.jsp";
	}

}


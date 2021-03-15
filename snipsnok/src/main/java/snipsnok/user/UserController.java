package snipsnok.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import snipsnok.security.CurrentUser;


@Controller
@RequestMapping(path="/api")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private CurrentUser currentUser;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(path="/users")
	public @ResponseBody String addNewUser (@RequestBody User user) {

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userService.addUser(user);
		return "Saved new user to database";
	}
	
	@GetMapping(path="/users")
	public @ResponseBody Iterable<User> getAllUsers() {

		return userService.findAllUsers();
	}

	/**
	 * Private end point for getting a specific user
	 * @param username : the name of the user
	 * Returns an user object with the corresponding ID
	 */
	@GetMapping(path="/users/{username}")
	public @ResponseBody User getUser(@PathVariable String username) {
		return userService.getUser(username);
	}

	@GetMapping(path="/users/current")
	public @ResponseBody User getCurrentUser() {return currentUser.getUser(); }

	/**
	 * Private end point for editing an user's profile
	 * @param newDetails: user object with the updated details
	 * Returns
	 */
	@PostMapping(path="/users/edit")
	public @ResponseBody User editUser(@RequestBody User newDetails){

		return userService.editUser(currentUser, newDetails);
	}

}

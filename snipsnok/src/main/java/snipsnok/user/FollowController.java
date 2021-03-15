package snipsnok.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snipsnok.security.CurrentUser;


@Controller
@RequestMapping(path="/api")
public class FollowController {
	@Autowired
	private FollowService followService;

	@Autowired
	private CurrentUser currentUser;
	
	@PostMapping(path="/follow/{username}")
	public @ResponseBody Follow addNewFollower (@PathVariable String username) {
		return followService.createFollow(currentUser.getUser(), username);

	}

	// Unfollow an account
	@DeleteMapping(path="/follow/{username}")
	public @ResponseBody void deleteExistingFollower (@PathVariable String username) {
		followService.deleteFollow(currentUser.getUser(), username);
		return;
	}

	// Get a follow
	//@GetMapping(path="/follow/{username}")
	//public @ResponseBody List<Follow> getFollower (@PathVariable String username) {
	//	return followService.getFollow(currentUser.getUser(), username);

	//}

	// Get a follow
	@GetMapping(path="/follow/{username}")
	public @ResponseBody int getFollowerExists (@PathVariable String username) {
		/*if(followService.getFollow(currentUser.getUser(), username).size() == 0){
			//int returnValue = 1;
			return 1;
		}
		else {
			//int returnValue = 0;
			return 2;
		}

		 */
		return followService.getFollow(currentUser.getUser(), username).size();

	}

	// Retrieve all accounts the user with the specified username is following
	@GetMapping(path="/{username}/following")
	public @ResponseBody List<User> accountsUserIsFollowing (@PathVariable String username) {
		return followService.accountsFollowing(username);

	}

	// Retrieve number of accounts the user is following
	@GetMapping(path="/{username}/following/total")
	public @ResponseBody int numberAccountsUserIsFollowing (@PathVariable String username) {
		return followService.accountsFollowing(username).size();

	}


	// Retrieve all accounts that follow the given user
	@GetMapping(path="/{username}/followers")
	public @ResponseBody List<User> accountsFollowingUser (@PathVariable String username) {
		return followService.accountsFollowed(username);

	}

	// Number of accounts that follow the given user
	@GetMapping(path="/{username}/followers/total")
	public @ResponseBody int numberAccountsFollowingUser (@PathVariable String username) {
		return followService.accountsFollowed(username).size();

	}


}

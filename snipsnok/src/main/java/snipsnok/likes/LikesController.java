package snipsnok.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import snipsnok.security.CurrentUser;

@Controller
@RequestMapping(path="/api")
public class LikesController {
	
	@Autowired
	private LikesService likesService;
	
	@Autowired
	private CurrentUser currentUser;
	
	/**
	 * Private end point for liking a content.
	 * This controller will make a new like if the user hasn't like a content before.
	 * It will also handle changing dislike to like 
	 * @param userId : user id of liker
	 * @param contentId : content id of content being liked
	 * @return 
	 */
	@PostMapping(path="/contents/{contentId}/like")
	public @ResponseBody Likes like (@PathVariable int contentId) {
		return likesService.like(currentUser.getUser(), contentId);
	}
	
	/**
	 * Private end point for disliking a content
	 * @param userId : user id of disliker
	 * @param contentId : content id of content being liked
	 * @return
	 */
	@PostMapping(path="/contents/{contentId}/dislike")
	public @ResponseBody Likes dislike (@PathVariable int contentId) {
		return likesService.dislike(currentUser.getUser(), contentId);
	}
	
	/**
	 * Private end point for disliking a content
	 * @param userId : user id of disliker
	 * @param contentId : content id of content being liked
	 * @return
	 */
	@GetMapping(path="/contents/{contentId}/checklike")
	public @ResponseBody boolean checkLike (@PathVariable int contentId) {
		return likesService.findIfLikedExist(currentUser.getUser(), contentId);
	}
}

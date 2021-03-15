package snipsnok.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import snipsnok.likes.LikesService;
import snipsnok.security.CurrentUser;


@Controller
@RequestMapping(path="/api")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	@Autowired
	private LikesService likesService;
	@Autowired
	private CurrentUser currentUser;
	
	/**
	 * Private end point for publishing a content
	 * @param content : content DTO that is being added
	 * @return the new content added
	 */
	@PostMapping(path="/contents")
	public @ResponseBody Content addNewContent (@RequestBody Content content) {
		return contentService.addContent(content, currentUser.getUser());
	}
	

	/**
	 * public end point for getting a content
	 * @param contentId : content id of the content being retrieve
	 * @return the content requested
	 */
	@GetMapping(path="/contents/{contentId}")
	public @ResponseBody Content getContentbyId(@PathVariable int contentId) {
		return contentService.findContentById(contentId);
	}
	
	/**
	 * private end point for modifying fields in a content
	 * @param contentId : content id of the content being modified
	 * @param updatedContent : content DTO with updated fields
	 * @return the content with updated fields
	 */
	@PostMapping(path="/contents/{contentId}")
	public @ResponseBody Content modifyContent(@RequestBody Content updatedContent, @PathVariable int contentId) {
		// the whole entity needs to be send back with updated field
		return contentService.updateContent(updatedContent, contentId, currentUser.getUser());
	}
	
	/**
	 * public end point for getting total likes of a content
	 * @param contentId : content id of the content being retrieve 
	 * @return total likes in integer
	 */
	@GetMapping(path="/contents/{contentId}/totallikes")
	public @ResponseBody int getTotalLikes(@PathVariable int contentId) {
		return likesService.findTotalLikesByContentId(contentId);
	}
	
	/**
	 * private end point for deleting a content
	 * @param contentId : content id of the content to be delete
	 */
	@DeleteMapping(path="/contents/{contentId}")
	public @ResponseBody Content deleteContentById(@PathVariable int contentId) {
		return contentService.deleteContentById(contentId, currentUser.getUser());
	}
	
	
	/**
	 * public end point for showing recent contents
	 * @param page : page number
	 * @param size : page size
	 * @return List of contents as Page object
	 */
	@GetMapping(path="/contents/recent")
	public @ResponseBody Page<Content> getRecentContents(@RequestParam int page, @RequestParam int size){
		return contentService.getRecentContents(page, size);
	}
	
	
	/**
	 * private end point for showing recommended content for a user
	 * @param page : page number
	 * @param size : page size
	 * @return List of contents as Page object 
	 */
	@GetMapping(path="/contents/recommended")
	public @ResponseBody Page<Content> getRecommendedContents(
			@RequestParam int page, @RequestParam int size){
		return contentService.getRecommendedContents(currentUser.getUser(), page, size);
	}
	
	/**
	 * public end point for listing all contents from a user
	 * @param username : username of the contents creator that we are querying for contents
	 * @return List of contents in no particular ordering
	 */
	@GetMapping(path="/{username}/contents")
	public @ResponseBody Page<Content> getContentFromUser(@PathVariable String username, 
			@RequestParam int page, @RequestParam int size) {
		return contentService.getContentsFromUser(username, page, size);
	}
	

}

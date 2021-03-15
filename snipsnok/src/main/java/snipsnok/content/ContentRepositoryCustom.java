package snipsnok.content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import snipsnok.content.exception.ContentEditForbiddenException;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.user.User;

// This is our DAO interface for Content
public interface ContentRepositoryCustom {
	
	/**
	 * DAO API for updating content
	 * @param updatedContent : the content with updated fields
	 * @param contentId : content id of the content being updated
	 * @return : the updated content
	 * @throws ContentNotFoundException
	 */
	Content updateContent(Content updatedContent, int contentId, User user) 
			throws ContentNotFoundException,  ContentEditForbiddenException;
	
	/**
	 * DAO API for getting recommendation
	 * @param userId : user id
	 * @param pageable : pageable object that indicates page number and page size
	 * @return Pageable of content list
	 */
	Page<Content> getRecommendation(User user, Pageable pageable);
	
	/**
	 * DAO API for deleting a content, performs cascade on likes linked to this content
	 * @param contentId
	 * @param user: user doing the deletion
	 */
	void deleteContent(int contentId, User user);
}

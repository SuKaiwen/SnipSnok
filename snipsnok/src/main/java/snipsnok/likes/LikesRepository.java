package snipsnok.likes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import snipsnok.user.User;

public interface LikesRepository extends JpaRepository<Likes, Integer>{
	
	/**
	 * DAO API for finding list of likes from a user
	 * @param contentId : content id of a content
	 * @param user
	 * @return List of likes by a user 
	 */
	List<Likes> findByContentIdAndUser(@Param("content_id")int contentId, 
									   @Param("user_id") User user);
	
	/**
	 * DAO API for finding total likes of a content
	 * @param contentId : content id of content being query
	 * @return List of likes for a content
	 */
	List<Likes> findByContentIdAndDislikeFalse(@Param("content_id")int contentId);
	
	/**
	 * DAO API for finding total dislikes of a content
	 * @param contentId : content id of content being query
	 * @return List of likes for a content
	 */
	List<Likes> findByContentIdAndDislikeTrue(@Param("content_id")int contentId);
}

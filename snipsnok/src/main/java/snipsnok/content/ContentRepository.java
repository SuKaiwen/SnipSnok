package snipsnok.content;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import snipsnok.user.User;

// also DAO
public interface ContentRepository extends JpaRepository<Content, Integer>, ContentRepositoryCustom{
	
	/**
	 * DAO API for querying content by a creator
	 * @param userId : user id
	 * @return list of content by the given user id
	 */
	List<Content> findByCreator(@Param("user_id") User user);
	List<Content> findByNameIgnoreCaseContaining(String name);
}

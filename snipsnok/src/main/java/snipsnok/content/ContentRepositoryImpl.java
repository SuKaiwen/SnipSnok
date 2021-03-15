package snipsnok.content;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import common.PageMaker;
import snipsnok.content.exception.ContentEditForbiddenException;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.user.User;

// This class is our content DAO implementation

@Repository
public class ContentRepositoryImpl implements ContentRepositoryCustom {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	public ContentRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Content updateContent(Content updatedContent, int contentId, User user) 
			throws ContentNotFoundException, ContentEditForbiddenException{
		Content c = entityManager.find(Content.class, contentId);
		if (c == null) {
			throw new ContentNotFoundException(contentId);
		}
		if (c.getCreator().getId() != user.getId()) {
			// throw exception for not allow to edit
			throw new ContentEditForbiddenException(contentId);
		}
		c.setDescription(updatedContent.getDescription());
		c.setMediaType(updatedContent.getMediaType());
		c.setName(updatedContent.getName());
		c.setPostType(updatedContent.getPostType());
		c.setPrice(updatedContent.getPrice());
		c.setMediaURL(updatedContent.getMediaURL());
		entityManager.merge(c);
		return c;
	}

	@Override
	public Page<Content> getRecommendation(User user, Pageable pageable) {
		TypedQuery<Content> query = entityManager.createQuery(
				"SELECT c \n" + 
				"FROM Content c \n" + 
				"JOIN Follow f ON c.creator = f.followId \n" +
				"WHERE f.user = :user \n" +
				"ORDER BY c.dateCreated DESC"
				, Content.class);
		
		List<Content> l = query.setParameter("user", user).getResultList();
		return PageMaker.extractPage(pageable,l);
	}

	@Override
	public void deleteContent(int contentId, User user) {
		Content c = entityManager.find(Content.class, contentId);
		if (c == null) {
			throw new ContentNotFoundException(contentId);
		}
		if (c.getCreator().getId() != user.getId()) {
			// throw exception for not allow to edit
			throw new ContentEditForbiddenException(contentId);
		}
		Query query = entityManager.createQuery(
				"DELETE \n" + 
				"FROM Likes l \n" + 
				"WHERE l.content = :content");
		query.setParameter("content", c).executeUpdate();
		
		Query query2 = entityManager.createQuery(
				"DELETE \n" + 
				"FROM DonationContent d \n" + 
				"WHERE d.content = :content");
		query2.setParameter("content", c).executeUpdate();
		
		entityManager.remove(c);
		entityManager.flush();
	}
	
}

package snipsnok.content;

import org.springframework.data.domain.Page;

import snipsnok.user.User;

public interface ContentService {
	
	public Content addContent(Content content, User user);
	
	public Content findContentById(int contentId);
	
	public Content updateContent(Content updatedContent, int contentId, User user);
	
	public Content deleteContentById(int contentId, User user);
	
	public Page<Content> getRecentContents(int page, int size);
	
	public Page<Content> getRecommendedContents(User user, int page, int size);
	
	public Page<Content> getContentsFromUser(String username, int page, int size);
	
}

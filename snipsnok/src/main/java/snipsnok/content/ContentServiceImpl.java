package snipsnok.content;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import common.PageMaker;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.user.User;
import snipsnok.user.UserNotFoundException;
import snipsnok.user.UserRepository;

@Service
public class ContentServiceImpl implements ContentService {
	
	private ContentRepository contentRepository;
	
	private UserRepository userRepository;
	
	public ContentServiceImpl(ContentRepository contentRepository, UserRepository userRepository) {
		this.contentRepository = contentRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Content addContent(Content content, User user) {
		content.setCreator(user);
		content.setTotalLikes(0);
		user.setCreator(true);
		userRepository.save(user);
		return contentRepository.save(content);
	}

	@Override
	public Content findContentById(int contentId) {
		return contentRepository.findById(contentId).orElseThrow( ()-> new ContentNotFoundException(contentId));
	}

	@Override
	@Transactional
	public Content updateContent(Content updatedContent, int contentId, User user) {
		return contentRepository.updateContent(updatedContent, contentId, user);
	}

	@Override
	public Page<Content> getContentsFromUser(String username, int page, int size) {
		List<User> u = userRepository.findByUsername(username);
		if (u.isEmpty()) {
			// no user exist
			throw new UserNotFoundException(username);
		}
		List<Content> contents = contentRepository.findByCreator(u.get(0));
		Pageable pageable = PageRequest.of(page, size);
		return PageMaker.extractPage(pageable, contents);
	}

	@Override
	@Transactional
	public Content deleteContentById(int contentId, User user) {
		contentRepository.deleteContent(contentId, user);
		return new Content();
	}

	@Override
	public Page<Content> getRecentContents(int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreated").descending());
		return contentRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Page<Content> getRecommendedContents(User user, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return contentRepository.getRecommendation(user, pageable);
	}

}

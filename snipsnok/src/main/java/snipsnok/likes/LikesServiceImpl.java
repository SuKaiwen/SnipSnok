package snipsnok.likes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.user.User;

@Service
public class LikesServiceImpl implements LikesService {
	@Autowired
	private LikesRepository likesRepository;
	@Autowired
	private ContentRepository contentRepository;
	
	@Override
	public Likes like(User user, int contentId) {
		// check if this user already liked
		List<Likes> l = likesRepository.findByContentIdAndUser(contentId, user);
		if (!l.isEmpty() && l.size() == 1) {
			l.get(0).setDislike(false);
			// we update the total like also
			List<Likes> currentTotal= likesRepository.findByContentIdAndDislikeFalse(contentId);
			l.get(0).getContent().setTotalLikes(currentTotal.size()+1);
			contentRepository.save(l.get(0).getContent());
			
			return likesRepository.save(l.get(0));
		}
		// else we make a new like
		Likes n = new Likes();
		Content c = contentRepository.findById(contentId).get();
		n.setUser(user);
		n.setContent(c);
		n.setDislike(false);
		// we update the total like also
		List<Likes> currentTotal= likesRepository.findByContentIdAndDislikeFalse(contentId);
		c.setTotalLikes(currentTotal.size()+1);
		contentRepository.save(c);
		
		return likesRepository.save(n);
	}

	@Override
	public Likes dislike(User user, int contentId) {
		List<Likes> l = likesRepository.findByContentIdAndUser(contentId, user);
		if (!l.isEmpty() && l.size() == 1) {
			l.get(0).setDislike(true);
			// we update the total like also
			List<Likes> currentTotal= likesRepository.findByContentIdAndDislikeFalse(contentId);
			l.get(0).getContent().setTotalLikes(currentTotal.size()-1);
			contentRepository.save(l.get(0).getContent());
			
			return likesRepository.save(l.get(0));
		}
		// else we make a new dislike
		Likes n = new Likes();
		Content c = contentRepository.findById(contentId).get();
		n.setUser(user);
		n.setContent(c);
		n.setDislike(true);
		// we update the total like also
		List<Likes> currentTotal= likesRepository.findByContentIdAndDislikeFalse(contentId);
		c.setTotalLikes(currentTotal.size()-1);
		contentRepository.save(c);
		
		return likesRepository.save(n);
	}

	@Override
	public int findTotalLikesByContentId(int contentId) {
		List<Likes> l = likesRepository.findByContentIdAndDislikeFalse(contentId);
		return l.size();
	}

	@Override
	public int findTotalDislikesByContentId(int contentId) {
		List<Likes> l = likesRepository.findByContentIdAndDislikeTrue(contentId);
		return l.size();
	}

	@Override
	public boolean findIfLikedExist(User user, int contentId) {
		List<Likes> l = likesRepository.findByContentIdAndUser(contentId, user);
		if (l.size() == 0) {
			return true;
		}
		return l.get(0).getDislike();
	}

}

package snipsnok.likes;

import snipsnok.user.User;

public interface LikesService {
	
	public Likes like(User user, int contentId);
	
	public Likes dislike(User user, int contentId);
	
	public int findTotalLikesByContentId(int contentId);
	
	public int findTotalDislikesByContentId(int contentId);
	
	public boolean findIfLikedExist(User user, int contentId);
}

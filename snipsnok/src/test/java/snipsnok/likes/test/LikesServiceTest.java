package snipsnok.likes.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.likes.Likes;
import snipsnok.likes.LikesRepository;
import snipsnok.likes.LikesServiceImpl;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class LikesServiceTest {
	
	@Mock
	private LikesRepository likesRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private ContentRepository contentRepository;
	
	@InjectMocks
	private LikesServiceImpl likesService;
	
	/**
	 * Test that likes service can handle making a new like object and 
	 * set them to like to the correct user and content
	 * @given a user and a content that the user wants to like
	 * @when like service tries to like the content
	 * @then the like object return should be linked to the correct content & user
	 * @throws Exception
	 */
	@Test
	public void LikeServiceTest() throws Exception {
		// given
		Likes expected = new Likes();
		User u = new User();
		Optional<Content> c = Optional.of(new Content());
		c.get().setName("test");
		u.setUsername("tester");
		

		List<Likes> l = new ArrayList<Likes>();
		
		// when
		when(likesRepository.findByContentIdAndUser(1, u)).thenReturn(l);
		when(contentRepository.findById(1)).thenReturn(c);
		when(likesRepository.save(Mockito.any(Likes.class))).thenReturn(expected);
		
		Likes actual = likesService.like(u, 1);
		
		// then
		
		assertThat(actual.getContent()).isSameAs(expected.getContent());
		assertThat(actual.getUser()).isSameAs(expected.getUser());
	}
	
	/**
	 * Test that likes service can handle modifying a dislike to like 
	 * @given a user and a content that the user disliked but wants to like
	 * @when like service tries to like the content again
	 * @then the like object return should be linked to the correct content & user & dislike = false
	 * @throws Exception
	 */
	@Test
	public void LikeFromDislikeServiceTest() throws Exception {
		// given
		Likes expected = new Likes();
		User u = new User();
		Content c = new Content();
		c.setName("test");
		u.setUsername("tester");
		expected.setUser(u);
		expected.setContent(c);
		expected.setDislike(true);

		List<Likes> l = new ArrayList<Likes>();
		l.add(expected);
		expected.setDislike(false);
		
		// when
		when(likesRepository.findByContentIdAndUser(1, u)).thenReturn(l);
		when(likesRepository.save(expected)).thenReturn(expected);
		
		Likes actual = likesService.like(u, 1);
		
		// then
		
		assertThat(actual.getContent()).isSameAs(expected.getContent());
		assertThat(actual.getUser()).isSameAs(expected.getUser());
		assertThat(actual.getDislike()).isEqualTo(false);
	}
	
	/**
	 * Test that dislikes service can handle making a new like object and 
	 * set them to like to the correct user and content & dislike = true
	 * @given a user and a content that the user wants to dislike
	 * @when like service tries to dislike the content
	 * @then the like object return should be linked to the correct content & user
	 * @throws Exception
	 */
	@Test
	public void DislikeTest() throws Exception {
		// given
		Likes expected = new Likes();
		User u = new User();
		Optional<Content> c = Optional.of(new Content());
		c.get().setName("test");
		u.setUsername("tester");
		expected.setUser(u);
		expected.setContent(c.get());
		expected.setDislike(true);
		
		List<Likes> l = new ArrayList<Likes>();
		
		// when
		when(likesRepository.save(Mockito.any(Likes.class))).thenReturn(expected);
		when(likesRepository.findByContentIdAndUser(1, u)).thenReturn(l);
		when(contentRepository.findById(1)).thenReturn(c);
		
		Likes actual = likesService.dislike(u, 1);
		
		// then
		
		assertThat(actual.getContent()).isSameAs(expected.getContent());
		assertThat(actual.getUser()).isSameAs(expected.getUser());
		assertThat(actual.getDislike()).isEqualTo(true);
	}
	
	/**
	 * Test that likes service can handle modifying a like to dislike 
	 * @given a user and a content that the user liked but wants to dislike
	 * @when like service tries to dislike the content again
	 * @then the like object return should be linked to the correct content & user & dislike = true
	 * @throws Exception
	 */
	@Test
	public void DislikeFromLikeTest() throws Exception {
		// given
		Likes expected = new Likes();
		User u = new User();
		Content c = new Content();
		c.setName("test");
		u.setUsername("tester");
		expected.setUser(u);
		expected.setContent(c);
		expected.setDislike(true);

		List<Likes> l = new ArrayList<Likes>();
		l.add(expected);
		
		// when
		when(likesRepository.findByContentIdAndUser(1, u)).thenReturn(l);
		when(likesRepository.save(expected)).thenReturn(expected);
		
		Likes actual = likesService.dislike(u, 1);
		
		// then
		
		assertThat(actual.getContent()).isSameAs(expected.getContent());
		assertThat(actual.getUser()).isSameAs(expected.getUser());
		assertThat(actual.getDislike()).isEqualTo(true);
	}
	
	/**
	 * Test that total like is return by the service correctly
	 * @given a user liked a content
	 * @when like service ask for total likes of a content
	 * @then return result as 1 like
	 * @throws Exception
	 */
	@Test
	public void findTotalLikesTest() throws Exception {
		
		// given
		List<Likes> l = new ArrayList<Likes>();
		
		// a content
		Content c = new Content();
		c.setName("test");
		
		// user1 like
		Likes like = new Likes();
		User u = new User();
		u.setUsername("tester");
		like.setUser(u);
		like.setContent(c);
		like.setDislike(false);
		
		l.add(like);
		
		// when
		when(likesRepository.findByContentIdAndDislikeFalse(1)).thenReturn(l);
		
		int actual = likesService.findTotalLikesByContentId(1);
		
		// then
		assertThat(actual).isSameAs(l.size());
		
	}
	
	/**
	 * Test that total dislike is return by the service correctly
	 * @given a user disliked a content
	 * @when like service ask for total dislikes of a content
	 * @then return result as 1 dislike
	 * @throws Exception
	 */
	@Test
	public void findTotalDislikesTest() throws Exception {
		
		// given
		List<Likes> l = new ArrayList<Likes>();
		
		// a content
		Content c = new Content();
		c.setName("test");
		
		// user1 like
		Likes like = new Likes();
		User u = new User();
		u.setUsername("tester");
		like.setUser(u);
		like.setContent(c);
		like.setDislike(true);
		
		l.add(like);
		
		// when
		when(likesRepository.findByContentIdAndDislikeFalse(1)).thenReturn(l);
		
		int actual = likesService.findTotalLikesByContentId(1);
		
		// then
		assertThat(actual).isSameAs(l.size());
		
	}
}

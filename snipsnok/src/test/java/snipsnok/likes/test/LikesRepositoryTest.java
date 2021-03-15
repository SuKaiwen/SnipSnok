package snipsnok.likes.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.likes.Likes;
import snipsnok.likes.LikesRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

//Integration test btw as testing any interaction with DB is NOT Unit test
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class LikesRepositoryTest {
	
	 @Autowired
	 private ContentRepository contentRepository;
	    
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private LikesRepository likesRepository;
	    
	/**
     * Testing to see if dependency injection works
     */
    @Test
    public void injectedComponentsAreNotNull(){
      assertThat(contentRepository).isNotNull();
      assertThat(userRepository).isNotNull();
      assertThat(likesRepository).isNotNull();
    }
    
    /**
     * Test for finding likes associate with a content by a user
     * @given a user with a content and a like by the user on that content
     * @when like repository try to find like associated with a content by a user
     * @then should return 1 like associated with it
     * @throws Exception
     */
    @Test
    public void whenFindByContentIdAndUserId_thenReturnListLikes() throws Exception{
    	// given 
    	User u = new User();
    	u.setEmail("inttest@email");
    	u.setUsername("intTest");
    	
    	u = userRepository.save(u);
    	
    	Content c = new Content();
    	c.setName("intTest1");
    	c.setCreator(u);
    	c = contentRepository.save(c);
    	
    	Likes l = new Likes();
    	l.setContent(c);
    	l.setDislike(false);
    	l.setUser(u);
    	likesRepository.save(l);
    	
    	// when
    	List<Likes> found = likesRepository.findByContentIdAndUser(c.getId(), u);
    	
    	// then
    	assertThat(found.size()).isEqualTo(1);
    	assertThat(found.get(0).getDislike()).isEqualTo(false);
    }
    
    /**
     * Test for finding likes associate with a content by a user
     * @given 2 users, a content and a like by 1 user on that content and a dislike by another user
     * @when like repository try to find total likes associated with a content 
     * @then should return 1 like associated with it
     * @throws Exception
     */
    @Test
    public void whenFindByContentIdAndDislikeFalse_thenReturnInt() throws Exception{
    	// given 
    	User u = new User();
    	u.setEmail("inttest@email");
    	u.setUsername("intTest");
    	
    	u = userRepository.save(u);
    	
    	User u2 = new User();
    	u2.setEmail("inttest@email");
    	u2.setUsername("intTest2");
    	
    	u2 = userRepository.save(u2);
    	
    	Content c = new Content();
    	c.setName("intTest1");
    	c.setCreator(u);
    	c = contentRepository.save(c);
    	
    	Likes l = new Likes();
    	l.setContent(c);
    	l.setDislike(false);
    	l.setUser(u);
    	likesRepository.save(l);
    	
    	Likes l2 = new Likes();
    	l2.setContent(c);
    	l2.setDislike(true);
    	l2.setUser(u2);
    	likesRepository.save(l2);
    	
    	// when
    	List<Likes> found = likesRepository.findByContentIdAndDislikeFalse(c.getId());
    	
    	// then
    	assertThat(found.size()).isEqualTo(1);
    	assertThat(found.get(0).getUser()).isSameAs(u);
    }
    
    /**
     * Test for finding dislikes associate with a content by a user
     * @given 2 users, a content and a like by 1 user on that content and a dislike by another user
     * @when like repository try to find total dislikes associated with a content 
     * @then should return 1 dislike associated with it
     * @throws Exception
     */
    @Test
    public void whenFindByContentIdAndDislikeTrue_thenReturnInt() throws Exception{
    	// given 
    	User u = new User();
    	u.setEmail("inttest@email");
    	u.setUsername("intTest");
    	
    	u = userRepository.save(u);
    	
    	User u2 = new User();
    	u2.setEmail("inttest@email");
    	u2.setUsername("intTest2");
    	
    	u2 = userRepository.save(u2);
    	
    	Content c = new Content();
    	c.setName("intTest1");
    	c.setCreator(u);
    	c = contentRepository.save(c);
    	
    	Likes l = new Likes();
    	l.setContent(c);
    	l.setDislike(false);
    	l.setUser(u);
    	likesRepository.save(l);
    	
    	Likes l2 = new Likes();
    	l2.setContent(c);
    	l2.setDislike(true);
    	l2.setUser(u2);
    	likesRepository.save(l2);
    	
    	// when
    	List<Likes> found = likesRepository.findByContentIdAndDislikeTrue(c.getId());
    	
    	// then
    	assertThat(found.size()).isEqualTo(1);
    	assertThat(found.get(0).getUser()).isSameAs(u2);
    }
}

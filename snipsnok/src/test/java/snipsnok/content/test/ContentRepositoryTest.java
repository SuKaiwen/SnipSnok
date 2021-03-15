package snipsnok.content.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.user.Follow;
import snipsnok.user.FollowRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

// Integration test btw as testing any interaction with DB is NOT Unit test
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ContentRepositoryTest {
 
    @Autowired
    private ContentRepository contentRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private FollowRepository followRepository;
    
    /**
     * Testing to see if dependency injection works
     */
    @Test
    public void injectedComponentsAreNotNull(){
      assertThat(contentRepository).isNotNull();
      assertThat(userRepository).isNotNull();
      assertThat(followRepository).isNotNull();
    }
    
    /**
     * Test repository custom auto implemented findByCreator()
     * @given a user "intTest" and a content by that user
     * @when repository try to find contents with creator as "intTest"
     * @then check if content return is 1
     * @throws Exception
     */
    @Test
    public void whenFindByCreator_thenReturnListContent() throws Exception{
    	// given 
    	User u = new User();
    	u.setEmail("inttest@email");
    	u.setUsername("intTest");
    	
    	userRepository.save(u);
    	
    	Content c = new Content();
    	c.setName("intTest1");
		c.setCreator(u);
    	contentRepository.save(c);
    	
    	// when
    	List<Content> found = contentRepository.findByCreator(u);
    	
    	// then
    	assertThat(found.size()).isEqualTo(1);
    }
    
    /**
     * Test repository custom implemented updateContent()
     * @given a user "intTest" and a content "intTest1" by that user
     * @when repository try to update the content
     * @then check if the content is updated
     * @throws Exception
     */
    @Test
    public void whenUpdateContent_thenReturnUpdatedContent() throws Exception{
    	// given 
    	User u = new User();
    	u.setEmail("inttest@email");
    	u.setUsername("intTest");
    	
    	userRepository.save(u);
    	
    	Content c = new Content();
    	c.setName("intTest1");
    	c.setCreator(u);
    	c = contentRepository.save(c);
    	
    	// check for before updating
    	assertThat(c.getName()).isEqualTo("intTest1");
    	assertThat(c.getCreator()).isSameAs(u);
    	
    	// when
    	c.setDescription("updated info");
    	c.setName("updated name");
    	c.setPrice(1.1);
    	Content found = contentRepository.updateContent(c, c.getId(), u);
    	
    	// then
    	assertThat(found.getName()).isEqualTo(c.getName());
    	assertThat(found.getDescription()).isEqualTo(c.getDescription());
    	assertThat(found.getPrice()).isEqualTo(c.getPrice());
    	assertThat(found.getCreator()).isSameAs(u);
    }
    
    /**
     * Test repository custom auto implemented getRecommendation()
     * @given a user "Test" and content "TestContent1" and "TestContent2" by that user
     * @given a user "Test2" follows the user "Test"
     * @when repository try to find recommended contents for "TestContent2"
     * @then check if paginated contents returned has page 0 with 1 content sorted by descending date
     * @throws Exception
     */
    @Test
    public void whenRecommendedContent1Page_thenReturnRecommendedContent1Page() throws Exception{
    	// given 
    	User user1 = new User();
    	user1.setEmail("inttest@email");
    	user1.setUsername("Test");
    	
    	User user2 = new User();
    	user2.setEmail("inttest2@email");
    	user2.setUsername("Test2");
    	
    	userRepository.save(user1);
    	userRepository.save(user2);
    	
    	Content c = new Content();
    	c.setName("TestContent1");
    	c.setCreator(user1);
    	c = contentRepository.save(c);
    	
    	Content c2 = new Content();
    	c2.setName("TestContent2");
    	c2.setCreator(user1);
    	c2 = contentRepository.save(c2);
    	
    	// given user2 follows user1
    	Follow f = new Follow();
    	f.setUser(user2);
		f.setFollowerId(user1.getId());
		followRepository.save(f);
    	
    	// when
    	Pageable pageable = PageRequest.of(0, 1);
    	Page<Content> found = contentRepository.getRecommendation(user2, pageable);
    	
    	// then
    	assertThat(found.getTotalPages()).isEqualTo(2);
    	assertThat(found.getSize()).isEqualTo(1);
    	assertThat(found.getContent().get(0)).isSameAs(c2);
    }
    
    /**
     * Test repository custom auto implemented getRecommendation()
     * @given a user "Test" and content "TestContent1" and "TestContent2" by that user
     * @given a user "Test3" and content "TestContent3" by that user
     * @given a user "Test2" follows the user "Test" and user "Test3"
     * @when repository try to find recommended contents for "Test2"
     * @then check if paginated contents returned has page 0 with size 3
     * @throws Exception
     */
    @Test
    public void whenRecommendedContentManyFollowing_thenReturnRecommendedContents() throws Exception{
    	// given 
    	User user1 = new User();
    	user1.setEmail("inttest@email");
    	user1.setUsername("Test");
    	
    	User user2 = new User();
    	user2.setEmail("inttest2@email");
    	user2.setUsername("Test2");
    	
    	User user3 = new User();
    	user3.setEmail("inttest3@email");
    	user3.setUsername("Test3");
    	
    	userRepository.save(user1);
    	userRepository.save(user2);
    	userRepository.save(user3);
    	
    	Content c = new Content();
    	c.setName("TestContent1");
    	c.setCreator(user1);
    	Content c2 = new Content();
    	c2.setName("TestContent2");
    	c2.setCreator(user1);
    	
    	Content c3 = new Content();
    	c3.setName("TestContent3");
    	c3.setCreator(user3);
    	
    	c = contentRepository.save(c);
    	c2 = contentRepository.save(c2);
    	
    	// given user2 follows user1
    	Follow f = new Follow();
    	f.setUser(user2);
		f.setFollowerId(user1.getId());
		followRepository.save(f);
		
		// given user2 follows user3
    	Follow f2 = new Follow();
    	f2.setUser(user2);
		f2.setFollowerId(user3.getId());
		followRepository.save(f2);
    	
    	// when
    	Pageable pageable = PageRequest.of(0, 3);
    	Page<Content> found = contentRepository.getRecommendation(user2, pageable);
    	
    	// then
    	assertThat(found.getTotalPages()).isEqualTo(1);
    	assertThat(found.getSize()).isEqualTo(3);
    }
}

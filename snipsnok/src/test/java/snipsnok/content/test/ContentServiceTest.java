package snipsnok.content.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.PageableExecutionUtils;

import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.content.ContentServiceImpl;
import snipsnok.content.exception.ContentNotFoundException;
import snipsnok.user.User;
import snipsnok.user.UserNotFoundException;
import snipsnok.user.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ContentServiceTest {
	
	@Mock
	private ContentRepository contentRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private ContentServiceImpl contentService;
	
	/**
	 * This test that the service is able to call the repository correctly
	 * @given a user who wants to add a new content
	 * @when ContentService try to add the new content
	 * @then the added content should be equal to the one given
	 * @throws Exception
	 */
	@Test
	public void addContentServiceTest() throws Exception {
		// given
		Content expected = new Content();
		User creator = new User();
		creator.setUsername("tester");
		expected.setName("test content");
		
		// when
		when(contentRepository.save(expected)).thenReturn(expected);
		
		Content actual = contentService.addContent(expected, creator);
		
		// then
		
		assertThat(actual.getName()).isSameAs(expected.getName());
		assertThat(actual.getCreator()).isSameAs(creator);
		
	}
	
	/**
	 * This test that the service is able to call the repository correctly
	 * @given a user who wants to find a content
	 * @when ContentService try to find it using content id 
	 * @then it should return the content associate to the id
	 * @throws Exception
	 */
	@Test
	public void findContentByIdServiceTest() throws Exception {
		// given
		Optional<Content> expected = Optional.of(new Content());
		expected.get().setName("test content");
		
		// when
		when(contentRepository.findById(1)).thenReturn(expected);
		
		Content actual = contentService.findContentById(1);
		
		// then
		
		assertThat(actual.getName()).isSameAs(expected.get().getName());
		
	}
	
	/**
	 * This test that the service is able to throw exception successfully
	 * @when ContentService try to find it using content id of non existing content
	 * @then we expected exception for content not found with the id
	 * @throws ContentNotFoundException
	 */
	@Test
	public void findContentByIdServiceExceptionTest() throws ContentNotFoundException {
		// when
		when(contentRepository.findById(1)).thenReturn(Optional.empty());
		
		// then
		assertThatExceptionOfType(ContentNotFoundException.class).isThrownBy(() -> {
			contentService.findContentById(1);
		});
		
	}
	
	/**
	 * This test that the service is able to call the repository correctly
	 * @given a user who wants to update a content
	 * @when ContentService try to update it 
	 * @then it should return the content with updated field
	 * @throws Exception
	 */
	@Test
	public void updateContentServiceTest() throws Exception {
		// given
		Optional<Content> expected = Optional.of(new Content());
		expected.get().setName("test updated content");
		User creator = new User();
		expected.get().setCreator(creator);
		
		// when
		when(contentRepository.updateContent(expected.get(), 1, creator)).thenReturn(expected.get());
		
		Content actual = contentService.updateContent(expected.get(), 1, creator );
		
		// then
		
		assertThat(actual.getName()).isSameAs(expected.get().getName());
		
	}
	
	/**
	 * This test that the service is able to call the repository correctly
	 * @given a user who wants to find a contents from another user
	 * @when ContentService try to find them using username
	 * @then it should return the contents associate to the username
	 * @throws Exception
	 */
	@Test
	public void getContentsFromUserServiceTest() throws Exception {
		// given
		List<Content> expected = new ArrayList<Content>();
		Content content = new Content();
		expected.add(content);
		List<User> users = new ArrayList<User>();
		User u = new User();
		u.setUsername("big creator");
		users.add(u);
		
		// when
		when(contentRepository.findByCreator(u)).thenReturn(expected);
		when(userRepository.findByUsername("big creator")).thenReturn(users);
		
		Page<Content> actual = contentService.getContentsFromUser("big creator",1,1);
		
		// then
		
		assertThat(actual.getSize()).isSameAs(expected.size());
		
	}
	
	/**
	 * This test that the service is able to throw exception successfully
	 * @given a user who wants to find a content from another non existing user
	 * @when ContentService try to find it using non existing username
	 * @then it should throw UserNotFoundException
	 * @throws UserNotFoundException
	 */
	@Test
	public void getContentsFromUserServiceExceptionTest() throws UserNotFoundException {
		// when
		when(userRepository.findByUsername("big creator")).thenReturn(new ArrayList<User>());
		
		// then
		assertThatExceptionOfType(UserNotFoundException.class).isThrownBy(() -> {
			contentService.getContentsFromUser("big creator",1,1);
		});
		
	}
	
	/**
	 * This test that the service is able to call the repository correctly
	 * @given a pagination config from controller for recent contents
	 * @when ContentService try to find any recent content id with given pagination 
	 * @then it should return recent contents as a pagination
	 * @throws Exception
	 */
	@Test
	public void getRecentContentsServiceTest() throws Exception {
		// given
		List<Content> filteredContents = new ArrayList<>();
	    LongSupplier totalSupplier = () -> {return 1;};
		Page<Content> expected = PageableExecutionUtils.getPage(filteredContents, PageRequest.of(1, 1),
                totalSupplier);
		Pageable pageable = PageRequest.of(1, 1, Sort.by("dateCreated").descending());
		
		// when
		when(contentRepository.findAll(pageable)).thenReturn(expected);
		
		Page<Content> actual = contentService.getRecentContents(1, 1);
		
		// then
		
		assertThat(actual.getSize()).isSameAs(expected.getSize());
		assertThat(actual.getSort()).isSameAs(expected.getSort());
		assertThat(actual.getNumberOfElements()).isSameAs(expected.getNumberOfElements());
	}
	
	/**
	 * This test that the service is able to call the repository correctly
	 * @given a pagination config from controller for recommendation
	 * @when ContentService try to find any recommended content id with given pagination and userId
	 * @then it should return recommended contents as a pagination ONLY from users that the userId follows
	 * @throws Exception
	 */
	@Test
	public void getRecommendedContentsServiceTest() throws Exception {
		// given
		List<Content> filteredContents = new ArrayList<>();
	    LongSupplier totalSupplier = () -> {return 1;};
		Page<Content> expected = PageableExecutionUtils.getPage(filteredContents, PageRequest.of(1, 1),
                totalSupplier);
		Pageable pageable = PageRequest.of(1, 1);
		User u = new User();
		
		// when
		when(contentRepository.getRecommendation(u, pageable)).thenReturn(expected);
		
		Page<Content> actual = contentService.getRecommendedContents(u, 1, 1);
		
		// then
		
		assertThat(actual.getSize()).isSameAs(expected.getSize());
		assertThat(actual.getNumberOfElements()).isSameAs(expected.getNumberOfElements());
	}
	
}

package snipsnok.content.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

import snipsnok.content.Content;
import snipsnok.content.ContentController;
import snipsnok.content.ContentService;
import snipsnok.likes.LikesService;
import snipsnok.security.CurrentUser;
import snipsnok.user.ApplicationUserDetailsService;
import snipsnok.user.User;

@WithMockUser
@WebMvcTest(ContentController.class)
public class ContentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ContentService contentService;
	
	@MockBean
	private LikesService likesService;

	@MockBean
	private ApplicationUserDetailsService ads;
	
	@MockBean
	private CurrentUser currentUser;
	
	private User u;
	
	@BeforeEach
	public void setup() {
		u = new User();
		when(currentUser.getUser()).thenReturn(u);
	}
		
	/**
	 * Testing for HTTP 200 OK when adding a content
	 * @throws Exception
	 */
	@Test
	public void addContentTest() throws Exception {
		Content expected = new Content();
		when(contentService.addContent(expected, u)).thenReturn(expected);
		mockMvc.perform(post("/api/contents")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(expected)))
				.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 405 when using DELETE on /contents
	 * @throws Exception
	 */
	@Test
	public void addContentNotAllowedDeleteTest() throws Exception {
		Content expected = new Content();
		when(contentService.addContent(expected, new User())).thenReturn(expected);
		mockMvc.perform(delete("/api/contents")
				.queryParam("userId", "1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(expected)))
		.andExpect(status().isMethodNotAllowed());
	}
	
	/**
	 * Testing for HTTP 200 OK when getting a content
	 * @throws Exception
	 */
	@Test
	public void getContentByIdTest() throws Exception {
		Content expected = new Content();
		when(contentService.findContentById(1)).thenReturn(expected);
		mockMvc.perform(get("/api/contents/{contentId}", 1))
		.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 200 OK when updating a content
	 * @throws Exception
	 */
	@Test
	public void updateContentTest() throws Exception {
		Content expected = new Content();
		when(contentService.updateContent(expected, 1, new User())).thenReturn(expected);
		mockMvc.perform(post("/api/contents/{contentId}", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(expected)))
		.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 200 OK when getting a content's total likes
	 * @throws Exception
	 */
	@Test
	public void getTotalLikesFromContentTest() throws Exception {
		when(likesService.findTotalLikesByContentId(1)).thenReturn(1);
		mockMvc.perform(get("/api/contents/{contentId}/totallikes", 1))
		.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 200 OK when deleting a content
	 * @throws Exception
	 */
	@Test
	public void deleteContentTest() throws Exception {
		Content empty = new Content();
		when(contentService.deleteContentById(1, u)).thenReturn(empty);
		mockMvc.perform(delete("/api/contents/{contentId}", 1))
		.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 200 OK when getting recent contents
	 * @throws Exception
	 */
	@Test
	public void getRecentContentsTest() throws Exception {
		List<Content> filteredContents = new ArrayList<>();
	    LongSupplier totalSupplier = () -> {return 1;};
		Page<Content> expected = PageableExecutionUtils.getPage(filteredContents, PageRequest.of(1, 1),
                totalSupplier);
		when(contentService.getRecentContents(1, 1)).thenReturn(expected);
		mockMvc.perform(get("/api/contents/recent")
			.queryParam("page", "1")
			.queryParam("size", "1"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 200 OK when getting recommended contents
	 * @throws Exception
	 */
	@Test
	public void getRecommendedContentsTest() throws Exception {
		List<Content> filteredContents = new ArrayList<>();
	    LongSupplier totalSupplier = () -> {return 1;};
		Page<Content> expected = PageableExecutionUtils.getPage(filteredContents, PageRequest.of(1, 1),
                totalSupplier);
		when(contentService.getRecommendedContents(new User(), 1, 1)).thenReturn(expected);
		mockMvc.perform(get("/api/contents/recommended")
			.queryParam("userId", "1")
			.queryParam("page", "1")
			.queryParam("size", "1"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Testing for HTTP 200 OK when getting contents from a user
	 * @throws Exception
	 */
	@Test
	public void getContentsFromUserTest() throws Exception {
		List<Content> filteredContents = new ArrayList<>();
	    LongSupplier totalSupplier = () -> {return 1;};
		Page<Content> expected = PageableExecutionUtils.getPage(filteredContents, PageRequest.of(1, 1),
                totalSupplier);
		when(contentService.getContentsFromUser("nick",1,1)).thenReturn(expected);
		mockMvc.perform(get("/api/{username}/contents", "nick")
				.queryParam("page", "1")
				.queryParam("size", "1"))
		.andExpect(status().isOk());
	}
}

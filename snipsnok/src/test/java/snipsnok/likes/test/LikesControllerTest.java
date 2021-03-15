package snipsnok.likes.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import snipsnok.likes.Likes;
import snipsnok.likes.LikesController;
import snipsnok.likes.LikesService;
import snipsnok.security.CurrentUser;
import snipsnok.user.ApplicationUserDetailsService;
import snipsnok.user.User;

@WithMockUser
@WebMvcTest(LikesController.class)
public class LikesControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
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
	 * Test for HTTP 200 OK when liking a content
	 * @throws Exception
	 */
	@Test
	public void LikeTest() throws Exception {
		Likes expected = new Likes();
		when(likesService.like(u, 1)).thenReturn(expected);
		mockMvc.perform(post("/api/contents/{contentId}/like",1)
				.queryParam("userId", "1"))
		.andExpect(status().isOk());
	}
	
	/**
	 * Test for HTTP 200 OK when disliking a content
	 * @throws Exception
	 */
	@Test
	public void DislikeTest() throws Exception {
		Likes expected = new Likes();
		expected.setDislike(true);
		when(likesService.dislike(u,1)).thenReturn(expected);
		mockMvc.perform(post("/api/contents/{contentId}/dislike",1)
				.queryParam("userId", "1"))
		.andExpect(status().isOk());
	}

}

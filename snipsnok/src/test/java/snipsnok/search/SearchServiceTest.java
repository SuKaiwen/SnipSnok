package snipsnok.search;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private ContentRepository contentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Test
    public void getContentByNameTest() throws Exception {
        Content content = new Content();
        User user = new User();
        user.setUsername("Test");
        content.setCreator(user);
        content.setName("Test");
        ArrayList expected = new ArrayList();
        expected.add(content);

        when(contentRepository.findByNameIgnoreCaseContaining("Test")).thenReturn(expected);

        Iterable actual = searchService.getContentByName("Test");
        assertThat(actual.iterator().next()).isSameAs(expected.iterator().next());
    }

    @Test
    public void getUserByUserNameTest() throws Exception {
        User user = new User();
        user.setUsername("Test");
        ArrayList expected = new ArrayList();
        expected.add(user);

        when(userRepository.findByUsernameIgnoreCaseContaining("Test")).thenReturn(expected);

        Iterable actual = searchService.getUsersByUserName("Test");
        assertThat(actual.iterator().next()).isSameAs(expected.iterator().next());
    }



}

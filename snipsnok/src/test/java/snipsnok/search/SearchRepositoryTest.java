package snipsnok.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SearchRepositoryTest {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Testing to see if dependency injection works
     */
    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(contentRepository).isNotNull();
        assertThat(userRepository).isNotNull();
    }
    //Test that Negative search returns empty list
    @Test
    public void searchUsersEmpty(){
        List<User> found = userRepository.findByUsernameIgnoreCaseContaining("WontFindThisUsername");
        assertThat(found.size()).isEqualTo(0);
    }
    //Test that Negative search returns empty list
    @Test
    public void searchContentEmpty(){
        List<Content> found = contentRepository.findByNameIgnoreCaseContaining("WontFindThisContentName");
        assertThat(found.size()).isEqualTo(0);
    }

    //Test that both users are found
    @Test
    public void findByUsernameLikeTest(){
        User ut = new User();
        ut.setEmail("test@email");
        ut.setUsername("GoodUser");

        User ub = new User();
        ub.setEmail("otherTest@email");
        ub.setUsername("GoodingUser");

        userRepository.save(ut);
        userRepository.save(ub);

        List<User> found = userRepository.findByUsernameIgnoreCaseContaining("good");
        assertThat(found.size()).isEqualTo(2);
    }

    //Test that both content are found
    @Test
    public void findByContentNameLikeTest(){
        User ut = new User();
        ut.setEmail("test@email");
        ut.setUsername("TestUser");

        userRepository.save(ut);

        Content ct = new Content();
        ct.setName("Search content test 2");
        ct.setCreator(ut);

        Content cb = new Content();
        cb.setName("Search content test 2");
        cb.setCreator(ut);

        contentRepository.save(ct);
        contentRepository.save(cb);

        List<Content> found = contentRepository.findByNameIgnoreCaseContaining("search");
        assertThat(found.size()).isEqualTo(2);

    }



    //Test that only one User is found
    @Test
    public void findByUsernameDifferentTest(){
        User ut = new User();
        ut.setEmail("test@email");
        ut.setUsername("GoodUser");

        User ub = new User();
        ub.setEmail("Bad@email");
        ub.setUsername("BadUser");

        userRepository.save(ut);
        userRepository.save(ub);

        List<User> found = userRepository.findByUsernameIgnoreCaseContaining("good");
        assertThat(found.size()).isEqualTo(1);
    }


    //Test that only one Content is found
    @Test
    public void findByContentNameDifferentTest(){
        User ut = new User();
        ut.setEmail("test@email");
        ut.setUsername("TestUser");

        userRepository.save(ut);

        Content ct = new Content();
        ct.setName("Search Good");
        ct.setCreator(ut);


        Content cb = new Content();
        cb.setName("Search Bad");
        cb.setCreator(ut);

        contentRepository.save(ct);
        contentRepository.save(cb);

        List<Content> found = contentRepository.findByNameIgnoreCaseContaining("bad");
        assertThat(found.size()).isEqualTo(1);

    }
}

package snipsnok.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snipsnok.content.Content;
import snipsnok.content.ContentRepository;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

@Service
public class SearchServiceImpl implements  SearchService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Iterable<User> getUsersByUserName(String query) {
        return userRepository.findByUsernameIgnoreCaseContaining(query);
    }

    @Override
    public Iterable<Content> getContentByName(String query) {
        return contentRepository.findByNameIgnoreCaseContaining(query);
    }
}
package snipsnok.search;

import snipsnok.content.Content;
import snipsnok.user.User;

public interface SearchService {
    Iterable<User> getUsersByUserName(String query);
    Iterable<Content> getContentByName(String query);
}
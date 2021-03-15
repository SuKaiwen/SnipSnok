package snipsnok.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import snipsnok.content.Content;
import snipsnok.user.User;

@Controller
@RequestMapping(path="/api")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping(path="/search/users")
    public @ResponseBody Iterable<User> searchUsersByUserName(@RequestParam String username) {
        return searchService.getUsersByUserName(username);
    }

    @GetMapping(path="/search/content")
    public @ResponseBody Iterable<Content> searchContent(@RequestParam String name) {
        return searchService.getContentByName(name);
    }

}
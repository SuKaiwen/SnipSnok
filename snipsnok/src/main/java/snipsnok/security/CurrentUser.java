package snipsnok.security;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import snipsnok.constants.Admin;
import snipsnok.user.User;
import snipsnok.user.UserRepository;

import java.util.List;

import snipsnok.constants.Admin.*;

@Component
public class CurrentUser {
    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public User getUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        Claims claims = (Claims) authentication.getPrincipal();
        String username = (String) claims.get("sub");

        List<User> users = userRepository.findByUsername(username);

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public boolean isAdmin(){
        User current = getUser();
        String userName = current.getUsername();

        return Admin.ADMINS.contains(userName);
    }
}
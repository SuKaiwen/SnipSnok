package snipsnok.constants;

public class SecurityConstants {
    public static final String SIGN_UP_URL = "/api/users";
    public static final String KEY = "bQeThWmZq4t7w!z%C*F-J@NcRfUjXn2r5u8x/A?D(G+KbPdSgVkYp3s6v9y$B&E)";
    public static final String HEADER_NAME = "Authorization";


    // in ms - a JWT token lasts for 1 year
    public static final Long EXPIRATION_TIME = 1000L*60*60*24*30*3;
    
    public static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html", 
            "/swagger-ui/**"
    };
}

package authenticate.pos.user;

import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public JpaUserDetailsService() {
    }

    public CustomUserDetails loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s = () -> {
            return new UsernameNotFoundException("Problem during authentication!");
        };
        User u = (User)this.userRepository.findUserByUsername(username).orElseThrow(s);
        return new CustomUserDetails(u);
    }
}

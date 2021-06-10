package recyclemug.ProjectMug.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.repository.UserRepositoryInterface;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryInterface userRepositoryInterface;

    public CustomUserDetailsService(UserRepositoryInterface userRepositoryInterface) {
        this.userRepositoryInterface = userRepositoryInterface;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepositoryInterface.findOneWithAuthoritiesByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(()-> new UsernameNotFoundException(email + "-> 데이터베이스에서 찾을 수 없습니다,"));
    }

    private User createUser(String email, recyclemug.ProjectMug.domain.user.User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(email + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}

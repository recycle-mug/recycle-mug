package recyclemug.ProjectMug.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.repository.CustomerRepositoryInterface;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomCustomerDetailsService implements UserDetailsService {

    private final CustomerRepositoryInterface customerRepositoryInterface;

    public CustomCustomerDetailsService(CustomerRepositoryInterface customerRepositoryInterface) {
        this.customerRepositoryInterface = customerRepositoryInterface;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepositoryInterface.findOneWithAuthoritiesByEmail(email)
                .map(customer -> createCustomer(email, customer))
                .orElseThrow(()-> new UsernameNotFoundException(email + "-> 데이터베이스에서 찾을 수 없습니다,"));
    }

    private User createCustomer(String email, Customer customer) {
        if (!customer.isActivated()) {
            throw new RuntimeException(email + " -> 활성화되어 있지 않습니다.");
        }
        List<GrantedAuthority> grantedAuthorities = customer.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        return new User(customer.getEmail(), customer.getPassword(), grantedAuthorities);
    }
}

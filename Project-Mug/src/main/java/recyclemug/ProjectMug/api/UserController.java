package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.User;
import recyclemug.ProjectMug.repository.UserRepository;
import recyclemug.ProjectMug.service.CustomerService;
import recyclemug.ProjectMug.service.PartnerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerService customerService;
    private final PartnerService partnerService;
    private final UserRepository userRepository;

    @GetMapping("/user/findAllUsers")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @GetMapping("/user/findAllCustomers")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Customer> getAllCustomers() {
        return customerService.findCustomers();
    }

    @GetMapping("/user/findAllPartners")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Partner> getAllPartners() {
        return partnerService.findPartners();
    }
}

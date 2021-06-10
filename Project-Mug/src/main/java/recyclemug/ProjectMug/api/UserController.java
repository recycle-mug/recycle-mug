package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import recyclemug.ProjectMug.domain.user.User;
import recyclemug.ProjectMug.service.CustomerService;
import recyclemug.ProjectMug.service.PartnerService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerService customerService;
    private final PartnerService partnerService;

    @GetMapping("/user/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<User> getUserInfo(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getCustomerWithAuthorities(email).get());
    }
}

package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import recyclemug.ProjectMug.data.CreateCustomerResponse;
import recyclemug.ProjectMug.data.CreatePartnerResponse;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.User;
import recyclemug.ProjectMug.repository.UserRepository;
import recyclemug.ProjectMug.service.CustomerService;
import recyclemug.ProjectMug.service.PartnerService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerService customerService;
    private final PartnerService partnerService;
    private final UserRepository userRepository;

    @GetMapping("/user/find-all-users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    @GetMapping("/user/find-all-customers")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<CreateCustomerResponse> getAllCustomers() throws IOException {
        List<Customer> customers = customerService.findCustomers();
        ArrayList<CreateCustomerResponse> customerResponses = new ArrayList<>();
        for (Customer customer : customers) {
            customerResponses.add(createCustomerResponse(customer));
        }
        return customerResponses;
    }

    @GetMapping("/user/find-all-partners")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<CreatePartnerResponse> getAllPartners() throws IOException {
        List<Partner> partners = partnerService.findPartners();
        List<CreatePartnerResponse> partnerResponses = new ArrayList<>();
        for (Partner partner : partners) {
            partnerResponses.add(createPartnerResponse(partner));
        }
        return partnerResponses;
    }

    public CreatePartnerResponse createPartnerResponse(Partner partner) throws IOException {
        CreatePartnerResponse response = CreatePartnerResponse.builder()
                .id(partner.getId())
                .email(partner.getEmail())
                .phoneNumber(partner.getPhoneNumber())
                .point(partner.getPoint())
                .nickname(partner.getNickname())
                .businessName(partner.getBusinessName())
                .address(partner.getAddress())
                .signupDateTIme(partner.getSignupDateTIme())
                .lastLoginDateTime(partner.getLastLoginDateTIme())
                .longitude(partner.getLongitude())
                .latitude(partner.getLatitude())
                .build();

        FileInputStream imageStream = new FileInputStream(partner.getProfilePictureAddress());
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        response.setProfilePicture(image);
        return response;
    }

    public CreateCustomerResponse createCustomerResponse(Customer customer) throws IOException {
        CreateCustomerResponse response = CreateCustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .point(customer.getPoint())
                .nickname(customer.getNickname())
                .phoneNumber(customer.getPhoneNumber())
                .customerState(customer.getCustomerState())
                .lastLoginDateTime(customer.getLastLoginDateTIme())
                .signupDateTIme(customer.getSignupDateTIme())
                .build();

        FileInputStream imageStream = new FileInputStream(customer.getProfilePictureAddress());
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        response.setProfilePicture(image);
        return response;
    }
}

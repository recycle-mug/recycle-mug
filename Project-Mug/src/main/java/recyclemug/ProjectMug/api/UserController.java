package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateCustomerResponse;
import recyclemug.ProjectMug.data.CreatePartnerResponse;
import recyclemug.ProjectMug.data.UpdateProfileImageRequest;
import recyclemug.ProjectMug.data.UpdateUserResponse;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.domain.user.User;
import recyclemug.ProjectMug.repository.UserRepository;
import recyclemug.ProjectMug.service.CustomerService;
import recyclemug.ProjectMug.service.PartnerService;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
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
    @PatchMapping("/user/customer/profile-image")
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @ResponseBody
    public ResponseEntity<UpdateUserResponse> updateCustomerProfileImage(@RequestBody UpdateProfileImageRequest request){
        try {
            Customer customer = customerService.findById(request.getUserId());
            customerService.modifyProfilePicture(request.getPictureAddress(), customer);
            log.info("ProfileImage changed. userId : " + request.getUserId());
            return new ResponseEntity<>(new UpdateUserResponse("success","Profile-image changed!"), HttpStatus.OK);
        } catch(NullPointerException e){
            log.error("Error to change profileImage.(NullpointException) userId : " + request.getUserId());
            return new ResponseEntity<>(new UpdateUserResponse("fail","NullpointException!"),HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            log.error("Error to change profileImage.(Exception) userId : " + request.getUserId());
            return new ResponseEntity<>(new UpdateUserResponse("fail","Invalid data!"),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/user/partner/profile-image")
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    public ResponseEntity<UpdateUserResponse> updatePartnerProfileImage(@RequestBody UpdateProfileImageRequest request){
        try{
            Partner partner = partnerService.findById(request.getUserId());
            partnerService.modifyProfilePicture(request.getPictureAddress(),partner);
            log.info("ProfileImage changed. userId : " + request.getUserId());
            return new ResponseEntity<>(new UpdateUserResponse("success","Profile-image changed!"),HttpStatus.OK);
        } catch(NullPointerException e){
            log.error("Error to change profileImage.(NullpointException) userId : " + request.getUserId());
            return new ResponseEntity<>(new UpdateUserResponse("fail","NullPointException!"),HttpStatus.BAD_REQUEST);
        } catch(Exception e){
            log.error("Error to change profileImage.(Exception) userId : " + request.getUserId());
            return new ResponseEntity<>(new UpdateUserResponse("fail","Invalid data!"),HttpStatus.BAD_REQUEST);
        }

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

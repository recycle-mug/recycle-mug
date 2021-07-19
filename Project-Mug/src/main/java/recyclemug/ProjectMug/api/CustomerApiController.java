package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateCustomerRequest;
import recyclemug.ProjectMug.data.CreateCustomerResponse;
import recyclemug.ProjectMug.data.CreateJoinResponse;
import recyclemug.ProjectMug.data.CreatePartnerResponse;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("/join/customer")
    @ResponseBody
    public CreateJoinResponse saveCustomer(@RequestBody @Valid CreateCustomerRequest request,
                                               HttpServletRequest httpServletRequest) {
        String picturePath = httpServletRequest.getServletContext().getRealPath("/images/users/default_user.jpg");

        try {
            Customer customer = Customer.createCustomer(request.getEmail(), request.getPassword(), request.getPhoneNumber(), picturePath);
            customerService.join(customer);
            return new CreateJoinResponse("success", "회원가입에 성공했습니다.");
        } catch (IllegalStateException e) {
            String message = e.toString();
            log.error("FAIL TO JOIN PARTNER: " + message);
        }
        return null;
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @ResponseBody
    public CreateCustomerResponse getCustomer(@PathVariable Long customerId) {
        try {
            Customer customer = customerService.findById(customerId);
            log.info("customer loaded");
            return createCustomerResponse(customer);
        } catch (NullPointerException e) {
            log.error("DB에 없는 Partner id 조회");
        } catch (FileNotFoundException e) {
            log.error("No image for customer id: " + customerId);
        } catch (IOException e) {
            log.error("IOException");
        }
        return null;
    }

    public CreateCustomerResponse createCustomerResponse(Customer customer) throws IOException {
        CreateCustomerResponse response = CreateCustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .point(customer.getPoint())
                .nickname(customer.getNickname())
                .phoneNumber(customer.getPhoneNumber())
                .customerState(customer.getCustomerState())
                .build();

        FileInputStream imageStream = new FileInputStream(customer.getProfilePictureAddress());
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        response.setProfilePicture(image);
        return response;
    }
}

package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.*;
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

    @PostMapping("/join/customer") // Customer 회원 가입
    @ResponseBody
    public CreateJoinResponse saveCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        String picturePath = "/home/ubuntu/images/users/default_user.jpg";

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

    @GetMapping("/customer/{customerId}") // 특정 Customer 정보 호출
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

    @PatchMapping("/customer/{customerId}") // 회원정보 수정 컨트롤러
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @ResponseBody
    public ResponseEntity<UpdateUserResponse> updateCustomerInfo(@PathVariable Long customerId,@RequestBody @Valid CustomerModifyDTO customerModifyDTO){
        try {
            Customer customer = customerService.findById(customerId);
            customerService.modifyCustomerInfo(customer,customerModifyDTO);
            log.info("Customer's info update complete.");
            return new ResponseEntity<>(new UpdateUserResponse("success", "Update customer's information"), HttpStatus.OK);
        }catch (RuntimeException e){
            log.error("Runtime Exception in customer's update info");
            return new ResponseEntity<>(new UpdateUserResponse("fail","Runtime Exception. Check your password"),HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Exception in customer's update info");
            return new ResponseEntity<>(new UpdateUserResponse("fail","Invalid data."),HttpStatus.BAD_REQUEST);
        }
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

package recyclemug.ProjectMug.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("/join/customer")
    public CreateCustomerResponse saveCustomerV1(@RequestBody @Valid CreateCustomerRequest request) {
        Customer customer = Customer.createCustomer(request.getId(), request.getPw(), request.getTel());
        Long customerId = customerService.join(customer);

        return new CreateCustomerResponse(customerId);
    }

    @Data
    static class CreateCustomerRequest {
        private String role; // "customer" or "partner"
        private String id; 	// String (email)
        private String pw;		// String
        private String tel;		// String
        private String address;	// String (partner일경우 "")
        private String address_num;	// String (5자리숫자, partner일경우 "")
        private String address_detail;
    }

    @Data
    static class CreateCustomerResponse {
        private Long id;

        public CreateCustomerResponse(Long id) {
            this.id = id;
        }
    }

}

package recyclemug.ProjectMug.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateCustomerRequest;
import recyclemug.ProjectMug.data.CreateCustomerResponse;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.service.CustomerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerApiController {

    private final CustomerService customerService;

    @PostMapping("/join/customer")
    public CreateCustomerResponse saveCustomer(@RequestBody @Valid CreateCustomerRequest request,
                                               HttpServletRequest httpServletRequest) {
        String picturePath = httpServletRequest.getServletContext().getRealPath("/images/users/default_user.jpg");
        Customer customer = Customer.createCustomer(request.getId(), request.getPw(), request.getTel(), picturePath);

        try {
            customerService.join(customer);
        } catch (IllegalStateException e) {
            String message = e.toString();
            return new CreateCustomerResponse("fail", message);
        }

        return new CreateCustomerResponse("success", "회원가입에 성공했습니다.");
    }


}

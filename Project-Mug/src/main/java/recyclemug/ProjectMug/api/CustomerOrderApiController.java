package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateOrderResponse;
import recyclemug.ProjectMug.data.CreateRentRequest;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.dto.CustomerOrderDto;
import recyclemug.ProjectMug.exception.CustomerStateNotAllowedException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.*;
import recyclemug.ProjectMug.service.CustomerOrderService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CustomerOrderApiController {
    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;
    private final PartnerCupRepository partnerCupRepository;
    private final CustomerOrderService customerOrderService;

    @GetMapping("/customer/rentCup")
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')") // partner가 customerQR코드에 접속
    @ResponseBody
    public CustomerOrderDto rentPartnerCupDTO(@RequestParam Long customerId, @RequestParam Long partnerId, @RequestParam Long cupId){
        String customerName = customerRepository.findOne(customerId).getNickname();
        String partnerBusinessName = partnerRepository.findOne(partnerId).getBusinessName();
        String partnerCup = partnerCupRepository.findById(cupId).getCup().getName();
        String partnerCupImage = partnerCupRepository.findById(cupId).getCup().getProfilePictureAddress();
        return new CustomerOrderDto(customerId,customerName,partnerId,partnerBusinessName,cupId,partnerCup,partnerCupImage);
    }

    @PostMapping("/customer/rentCup")
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')") // partner가 승인버튼을 눌렀을 때
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> rentPartnerCup(@RequestBody @Valid CreateRentRequest request){ // data에 Request,Response 추가해서 수정
        Customer customer = customerRepository.findOne(request.getCustomerId());
        PartnerCup partnerCup = partnerCupRepository.findById(request.getPartnerCupId());
        try {
            customerOrderService.cupOrderOfCustomer(customer, partnerCup);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NotEnoughStockException e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Not Enough point Exception"), HttpStatus.BAD_REQUEST);
        }catch (CustomerStateNotAllowedException e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Customer state not allowed"), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }

}

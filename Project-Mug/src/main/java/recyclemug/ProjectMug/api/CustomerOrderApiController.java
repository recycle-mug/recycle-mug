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
import recyclemug.ProjectMug.data.CreateReturnRequest;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.dto.CustomerOrderDto;
import recyclemug.ProjectMug.dto.CustomerReturnDto;
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

    @GetMapping("/customer/rent-cup")
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')") // partner가 customerQR코드에 접속
    @ResponseBody
    public CustomerOrderDto rentPartnerCupDTO(@RequestParam Long customerId, @RequestParam Long partnerCupId){
        String customerName = customerRepository.findOne(customerId).getNickname();
        PartnerCup partnerCup = partnerCupRepository.findById(partnerCupId);
        Long partnerId = partnerCup.getPartner().getId();
        String partnerBusinessName = partnerCup.getPartner().getBusinessName();
        Long cupId = partnerCupId;
        String partnerCupName = partnerCup.getCup().getName();
        String partnerCupImage = partnerCup.getCup().getProfilePictureAddress();
        return new CustomerOrderDto(customerId,customerName,partnerId,partnerBusinessName,cupId,partnerCupName,partnerCupImage);
    }

    @PostMapping("/customer/rent-cup")
    @PreAuthorize("hasAnyRole('PARTNER','ADMIN')") // partner가 승인버튼을 눌렀을 때
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> rentPartnerCup(@RequestBody @Valid CreateRentRequest request){ // data에 Request,Response 추가해서 수정
        Customer customer = customerRepository.findOne(request.getCustomerId());
        PartnerCup partnerCup = partnerCupRepository.findById(request.getPartnerCupId());
        try {
            customerOrderService.cupOrderOfCustomer(customer, partnerCup);
            log.info("Customer(" + customer.getNickname() + ") rents cup at the " + partnerCup.getPartner().getNickname());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NotEnoughStockException e){
            log.error("Not Enuough Point Exception");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Not Enough point Exception"), HttpStatus.BAD_REQUEST);
        }catch (CustomerStateNotAllowedException e){
            log.error("Customer state not allowed");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Customer state not allowed"), HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Invalid access");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/return-cup/normal") // customer가 일반적인 cup 반납 UI 접속
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @ResponseBody
    public CustomerReturnDto returnPartnerCupNormalDTO(@RequestParam Long partnerId){
        Partner partner = partnerRepository.findOne(partnerId);
        return new CustomerReturnDto(partnerId,partner.getBusinessName(),"safe");
    }

    @PostMapping("/customer/return-cup/normal") // customer cup 반납
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> returnPartnerCupNormal(@RequestBody @Valid CreateReturnRequest request){
        try{
            Customer customer = customerRepository.findOne(request.getCustomerId());
            Partner partner = partnerRepository.findOne(request.getPartnerId());
            customerOrderService.cupReturnOfCustomer(customer,partner);
            log.info("Return cup normal customer : " + customer.getNickname() + " Partner : " + partner.getNickname());
            return new ResponseEntity<>(new CreateOrderResponse("success","Return Complete"),HttpStatus.OK);
        }catch(Exception e){
            log.error("Customer return cup exception");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Exception Error"),HttpStatus.BAD_REQUEST);
        }
    }
}

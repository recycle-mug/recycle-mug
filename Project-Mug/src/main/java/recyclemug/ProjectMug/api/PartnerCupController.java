package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import recyclemug.ProjectMug.data.CreateOrderResponse;
import recyclemug.ProjectMug.data.CreatePartnerCupRequest;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;
import recyclemug.ProjectMug.service.PartnerOrderService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerCupController {
    private final PartnerOrderService partnerOrderService;
    private final PartnerOrderRepository partnerOrderRepository;

    @PostMapping("/partnerCup/add") // partner의 cup 대여 신청을 admin이 승인했을 시 partnerCup 등록
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> addPartnerCup(@RequestBody @Valid CreatePartnerCupRequest request){
        PartnerOrder partnerOrder = partnerOrderRepository.findById(request.getPartnerOrderId());
        try{
            partnerOrderService.completeOrder(partnerOrder);
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("success","complete"),HttpStatus.OK);
        }catch (NotEnoughStockException e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Not enough cup"), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/partnerCup/reject") // partner의 cup 대여 신청을 admin이 거부했을 시 partnerOrder 거부처리
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> rejectPartnerCup(@RequestBody @Valid CreatePartnerCupRequest request){
        PartnerOrder partnerOrder = partnerOrderRepository.findById(request.getPartnerOrderId());
        try{
            partnerOrderService.rejectOrder(partnerOrder);
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("success","reject"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }
}

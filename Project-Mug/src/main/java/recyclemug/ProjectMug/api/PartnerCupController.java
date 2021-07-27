package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateOrderResponse;
import recyclemug.ProjectMug.data.CreatePartnerCupRequest;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.PartnerCupRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.service.PartnerOrderService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerCupController {
    private final PartnerRepository partnerRepository;
    private final PartnerOrderService partnerOrderService;
    private final PartnerCupRepository partnerCupRepository;
    private final PartnerOrderRepository partnerOrderRepository;

    @GetMapping("/partner-cup/{partnerId}")
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @ResponseBody
    public List<PartnerCup> getPartnerCup(@RequestParam Long partnerId){
        Partner partner = partnerRepository.findOne(partnerId);
        List<PartnerCup> partnerCups = partnerCupRepository.findCupOfPartner(partner);
        return partnerCups;
    }

    @PostMapping("/partner-cup/add") // partner의 cup 대여 신청을 admin이 승인했을 시 partnerCup 등록
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> addPartnerCup(@RequestBody @Valid CreatePartnerCupRequest request){
        PartnerOrder partnerOrder = partnerOrderRepository.findById(request.getPartnerOrderId());
        try{
            partnerOrderService.completeOrder(partnerOrder);
            log.info("Admin accept partner's order   id : " + partnerOrder.getId());
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("success","complete"),HttpStatus.OK);
        }catch (NotEnoughStockException e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Not enough cup"), HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/partner-cup/reject") // partner의 cup 대여 신청을 admin이 거부했을 시 partnerOrder 거부처리
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> rejectPartnerCup(@RequestBody @Valid CreatePartnerCupRequest request){
        PartnerOrder partnerOrder = partnerOrderRepository.findById(request.getPartnerOrderId());
        try{
            partnerOrderService.rejectOrder(partnerOrder);
            log.info("Admin reject partner's order   id : " + partnerOrder.getId());
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("success","reject"),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Invalid access"), HttpStatus.BAD_REQUEST);
        }
    }
}

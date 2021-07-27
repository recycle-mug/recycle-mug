package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateOrderRequest;
import recyclemug.ProjectMug.data.CreateOrderResponse;
import recyclemug.ProjectMug.data.DeletePartnerOrderRequest;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.order.OrderState;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.dto.CupResponseDto;
import recyclemug.ProjectMug.dto.OrderDto;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.service.PartnerOrderService;
import recyclemug.ProjectMug.service.PartnerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerOrderApiController {

    private final PartnerOrderService partnerOrderService;
    private final PartnerService partnerService;
    private final CupRepository cupRepository;
    private final PartnerOrderRepository partnerOrderRepository;

    /**
     * Partner가 admin에게 주문한 컵,컵 개수, 주문 날짜 들을 state에 상관없이 모두 반환
     * @return
     */
    @GetMapping("/partner/orders")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getPartnerOrdersAll(){
        List<PartnerOrder> allOrder = partnerOrderRepository.findAllPartnerOrder();
        List<CupResponseDto> orderListForAdmin = new ArrayList<>();
        for(PartnerOrder partnerOrder : allOrder){
            CupResponseDto dto = new CupResponseDto(partnerOrder.getId(),
                    partnerOrder.getPartner().getBusinessName(),
                    partnerOrder.getCup().getName(),
                    partnerOrder.getOrderQuantity(),
                    partnerOrder.getOrderDateTime(),
                    partnerOrder.getOrderState());
            orderListForAdmin.add(dto);
        }
        return orderListForAdmin;
    }

    /**
     * Partner가 admin에게 주문한 컵,컵 개수, 주문 날짜들을 1개 반환
     * @param orderId
     * @return
     */
    @GetMapping("/partner/order/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public CupResponseDto getPartnerOrder(@PathVariable Long orderId) {
        PartnerOrder partnerOrder = partnerOrderRepository.findById(orderId);
        return new CupResponseDto(partnerOrder.getId(),
                partnerOrder.getPartner().getBusinessName(),
                partnerOrder.getCup().getName(),
                partnerOrder.getOrderQuantity(),
                partnerOrder.getOrderDateTime(),
                partnerOrder.getOrderState());
    }

    /**
     * Partner가 admin에게 주문한 컵,컵 개수, 주문 날짜들을 state별로 반환
     * @param state : "wait"(승인 대기 중),"cancel"(거부),"complete"(승인)
     * @return
     */
    @GetMapping("/partner/order/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getPartnerOrdersByState(@RequestParam("state") String state){
        List<PartnerOrder> partnerOrder;
        List<CupResponseDto> stateDto = new ArrayList<>();
        if (state.equals("wait")){
            partnerOrder = partnerOrderRepository.findPartnerOrder(OrderState.DELIVERY_WAITING);
        }else if (state.equals("cancel")){
            partnerOrder = partnerOrderRepository.findPartnerOrder(OrderState.CANCEL);
        }else if (state.equals("complete")){
            partnerOrder = partnerOrderRepository.findPartnerOrder(OrderState.COMPLETE);
        }else{
            partnerOrder = partnerOrderRepository.findAllPartnerOrder();
        }
        for (PartnerOrder order : partnerOrder){
            CupResponseDto dto = new CupResponseDto(order.getId(),
                    order.getPartner().getBusinessName(),
                    order.getCup().getName(),
                    order.getOrderQuantity(),
                    order.getOrderDateTime(),
                    order.getOrderState());
            stateDto.add(dto);
        }
        return stateDto;
    }

    @PostMapping("/partner/cup/order")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARTNER')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> partnerCupOrder(@RequestBody @Valid CreateOrderRequest request){
        try{
            Cup cup = cupRepository.findByCupId(request.getCupId());
            Partner partner = partnerService.findById(request.getPartnerId());
            partnerOrderService.cupOrderOfPartner(cup,partner,request.getStockQuantity());
            log.info(partner.getBusinessName() + " orders cups : " + cup.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NotEnoughPointException e){
            log.error("Not Enough point Exception");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Not Enough point Exception"), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            log.error("Invalid data : Partner's Order");
            return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid data : Partner's Order"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/partner/cup/order/delete")
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> partnerCupOrderDelete(@RequestBody @Valid DeletePartnerOrderRequest request){
        try {
            partnerOrderService.removeOrder(request.getOrderId());
            log.info("Delete partner's order : " + request.getOrderId());
            return new ResponseEntity<>(new CreateOrderResponse("success","Delete complete"),HttpStatus.OK);
        }catch(Exception e){
         log.error("Invalid partner's order");
         return new ResponseEntity<>(new CreateOrderResponse("fail","Invalid data!"),HttpStatus.BAD_REQUEST);
        }
    }
}

package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.order.OrderState;
import recyclemug.ProjectMug.dto.CupResponseDto;
import recyclemug.ProjectMug.repository.CustomerOrderRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final PartnerOrderRepository partnerOrderRepository;
    private final CustomerOrderRepository customerOrderRepository;

    @GetMapping("/admin/orderlist")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getOrderListAll(){
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

    @GetMapping("/admin/orderlist/{orderId}")
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
    @GetMapping("/admin/orderlist/filter")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getStateOrder(@RequestParam("state") String state){
        List<PartnerOrder> partnerOrder;
        List<CupResponseDto> stateDto = new ArrayList<>();
        if (state == "wait"){
            partnerOrder = partnerOrderRepository.findWaitPartnerOrder();
        }else if (state == "cancel"){
            partnerOrder = partnerOrderRepository.findCanceledPartnerOrder();
        }else if (state == "complete"){
            partnerOrder = partnerOrderRepository.findCompletePartnerOrder();
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

}

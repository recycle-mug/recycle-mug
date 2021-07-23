package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.dto.CupResponseDto;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final PartnerOrderRepository partnerOrderRepository;

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

    @GetMapping("/admin/orderlist/filter/wait")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getWaitOrder(){
        List<PartnerOrder> waitPartnerOrder = partnerOrderRepository.findWaitPartnerOrder();
        List<CupResponseDto> waitDto = new ArrayList<>();
        for(PartnerOrder partnerOrder : waitPartnerOrder){
            CupResponseDto dto = new CupResponseDto(partnerOrder.getId(),
                    partnerOrder.getPartner().getBusinessName(),
                    partnerOrder.getCup().getName(),
                    partnerOrder.getOrderQuantity(),
                    partnerOrder.getOrderDateTime(),
                    partnerOrder.getOrderState());
            waitDto.add(dto);
        }
        return waitDto;
    }

    @GetMapping("/admin/orderlist/filter/complete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getCompleteOrder(){
        List<PartnerOrder> completePartnerOrder = partnerOrderRepository.findCompletePartnerOrder();
        List<CupResponseDto> completeDto = new ArrayList<>();
        for(PartnerOrder partnerOrder : completePartnerOrder){
            CupResponseDto dto = new CupResponseDto(partnerOrder.getId(),
                    partnerOrder.getPartner().getBusinessName(),
                    partnerOrder.getCup().getName(),
                    partnerOrder.getOrderQuantity(),
                    partnerOrder.getOrderDateTime(),
                    partnerOrder.getOrderState());
            completeDto.add(dto);
        }
        return completeDto;
    }

    @GetMapping("/admin/orderlist/filter/cancel")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CupResponseDto> getCancelOrder(){
        List<PartnerOrder> cancelPartnerOrder = partnerOrderRepository.findCanceledPartnerOrder();
        List<CupResponseDto> cancelDto = new ArrayList<>();
        for(PartnerOrder partnerOrder : cancelPartnerOrder){
            CupResponseDto dto = new CupResponseDto(partnerOrder.getId(),
                    partnerOrder.getPartner().getBusinessName(),
                    partnerOrder.getCup().getName(),
                    partnerOrder.getOrderQuantity(),
                    partnerOrder.getOrderDateTime(),
                    partnerOrder.getOrderState());
            cancelDto.add(dto);
        }
        return cancelDto;
    }
//    @PostMapping("/admin/orderlist/{orderId}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @ResponseBody
//    public ResponseEntity<CreateAdminResponse> saveOrderState(@RequestBody @Valid CreateAdminRequest request, @PathVariable Long orderId){
//        try {
//            if (request.getState() == "complete" || request.getState() == "reject") {
//                partnerOrderRepository.updateOrderState(orderId, request.getState());
//                return new ResponseEntity<CreateAdminResponse>(new CreateAdminResponse("success", "State update!"), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<CreateAdminResponse>(new CreateAdminResponse("fail","Invalid state!"),HttpStatus.BAD_REQUEST);
//            }
//        }catch(Exception e){
//            return new ResponseEntity<CreateAdminResponse>(new CreateAdminResponse("fail","Exception!"),HttpStatus.BAD_REQUEST);
//        }
//    }
}

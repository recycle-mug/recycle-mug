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
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.dto.OrderDto;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.service.PartnerOrderService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value="/partner/cup/order")
public class PartnerOrderApiController {

    private final PartnerOrderService partnerOrderService;
    private final PartnerRepository partnerRepository;
    private final CupRepository cupRepository;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @ResponseBody
    public List<OrderDto> partnerOrderList(){
        List<Cup> Cups = cupRepository.findAllCups();
        List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
        for(Cup cup : Cups){
            OrderDto dto = new OrderDto(cup.getId(),cup.getName(),cup.getPrice());
            orderDtoList.add(dto);
        }
        return orderDtoList;
    }

    @RequestMapping (method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ADMIN', 'PARTNER')")
    @ResponseBody
    public ResponseEntity<CreateOrderResponse> partnerCupOrder(@RequestBody @Valid CreateOrderRequest request){
        try{
            Cup cup = cupRepository.findByCupId(request.getCupId());
            Partner partner = partnerRepository.findOne(request.getPartnerId());
            partnerOrderService.cupOrderOfPartner(cup,partner,request.getStockQuantity());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NotEnoughPointException e){
            log.error("Not Enough point Exception");
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Not Enough point Exception"), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            log.error("Invalid data : Partner's Order");
            return new ResponseEntity<CreateOrderResponse>(new CreateOrderResponse("fail","Invalid data : Partner's Order"), HttpStatus.BAD_REQUEST);
        }
    }
}

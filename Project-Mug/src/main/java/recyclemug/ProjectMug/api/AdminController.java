package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.order.OrderState;
import recyclemug.ProjectMug.dto.CupResponseDto;
import recyclemug.ProjectMug.dto.OrderDto;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.repository.CustomerOrderRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final CupRepository cupRepository;

    @GetMapping("/get-all-cups") // Partner가 admin에게 주문가능한 컵 내역들을 반환
    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @ResponseBody
    public List<OrderDto> availablePartnerCup(){
        List<Cup> Cups = cupRepository.findAllCups();
        List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
        for(Cup cup : Cups){
            OrderDto dto = new OrderDto(cup.getId(),cup.getName(),cup.getPrice());
            orderDtoList.add(dto);
        }
        return orderDtoList;
    }
}

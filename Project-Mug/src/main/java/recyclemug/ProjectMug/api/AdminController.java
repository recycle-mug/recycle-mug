package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.order.OrderState;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.dto.CustomerTradeDto;
import recyclemug.ProjectMug.dto.OrderDto;
import recyclemug.ProjectMug.dto.OrderSummaryDto;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.repository.CustomerOrderRepository;
import recyclemug.ProjectMug.repository.CustomerRepository;
import recyclemug.ProjectMug.repository.PartnerOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final CupRepository cupRepository;
    private final PartnerOrderRepository partnerOrderRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;

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

    @GetMapping("/get-order-summary") // Admin Page에 OrderSummary
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public OrderSummaryDto getOrderSummary(){
        int wait = partnerOrderRepository.findPartnerOrder(OrderState.DELIVERY_WAITING).size();
        int complete = partnerOrderRepository.findPartnerOrder(OrderState.COMPLETE).size();
        int cancel = partnerOrderRepository.findPartnerOrder(OrderState.CANCEL).size();
        int total = partnerOrderRepository.findAllPartnerOrder().size();
        return new OrderSummaryDto(wait,complete,cancel,total);
    }

    @GetMapping("/get-all-trade")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<CustomerTradeDto> getCustomerAllTrade(){
        List<CustomerOrder> customerOrders = customerOrderRepository.findAll();
        List<CustomerTradeDto> result = new ArrayList<>();
        for(CustomerOrder customerOrder : customerOrders){
            CustomerTradeDto customerTradeDto = new CustomerTradeDto(customerOrder.getCustomer().getEmail(),
                    customerOrder.getCustomer().getNickname(),
                    customerOrder.getRentDateTime(),
                    customerOrder.getReturnDateTime(),
                    customerOrder.getReturnedDateTime()
            );
            result.add(customerTradeDto);
        }
        return result;
    }
}

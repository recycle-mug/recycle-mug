package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerReturn;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.CustomerState;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.CustomerStateNotAllowedException;
import recyclemug.ProjectMug.exception.NoCupsForReturnException;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.CustomerOrderRepository;
import recyclemug.ProjectMug.repository.PartnerReturnRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final PartnerReturnRepository partnerReturnRepository;

    /**
     * Customer 의 Partner 에게 컵 주문시 메서드
     * @param customer
     * @param partnerCup
     * @throws NotEnoughPointException
     * @throws NotEnoughStockException
     * @throws CustomerStateNotAllowedException
     */
    @Transactional
    public void cupOrderOfCustomer(Customer customer, PartnerCup partnerCup) throws NotEnoughPointException, NotEnoughStockException, CustomerStateNotAllowedException {
        if (partnerCup.getCup().getPrice() > customer.getPoint()) {
            throw new NotEnoughPointException();
        } else if (partnerCup.getStockQuantity() < 1) {
            throw new NotEnoughStockException();
        } else if (!customer.getCustomerState().equals(CustomerState.NONE)) {
            throw new CustomerStateNotAllowedException();
        } else {
            CustomerOrder customerOrder = new CustomerOrder(customer, partnerCup.getCup());
            partnerCup.customerOrderComplete(1);
            customer.completeOrderCup(partnerCup.getCup().getPrice());
            customerOrderRepository.saveCup(customerOrder);
        }
    }

    /**
     * Customer 가 컵을 Partner 에게 반납하는 메서드 (미완성)
     * @param customer
     */
    @Transactional
    public void cupReturnOfCustomer(Customer customer, Partner partner) throws NoCupsForReturnException{
        if (customer.getCustomerState() == CustomerState.USE || customer.getCustomerState() == CustomerState.OVERDUE) {
            CustomerOrder lastOrder = customerOrderRepository.findLastOrderOfCustomer(customer.getId());
            if (lastOrder.getReturnedDateTime() != null) {
                log.error("no cups for return for customer id: " + customer.getId());
                customer.completeReturnCup(0, CustomerState.NONE);
                throw new NoCupsForReturnException();
            }

            Cup orderCup = lastOrder.getCup();
            int cupPrice = orderCup.getPrice();

            customer.completeReturnCup(cupPrice, CustomerState.NONE);

            lastOrder.setReturnedDateTime(LocalDateTime.now());
            List<PartnerReturn> partnerReturnList = partnerReturnRepository.findByPartnerIdAndCupId(partner.getId(), orderCup.getId());

            if (partnerReturnList.isEmpty()) {
                PartnerReturn partnerReturn = new PartnerReturn(partner, orderCup, 1);
                partnerReturnRepository.save(partnerReturn);
            } else {
                PartnerReturn partnerReturn = partnerReturnList.get(0);
                partnerReturn.setReturnQuantity(partnerReturn.getReturnQuantity() + 1);
            }
        } else {
            log.error("CustomerState is not USE");
            throw new NoCupsForReturnException("CustomerState is not USE");
        }
    }

    /**
     * Customer 가 컵을 파손, 분실하여 컵이 삭제되는 메서드
     * @param customer
     */
    @Transactional
    public void cupRemoveOfCustomer(Customer customer) throws NoCupsForReturnException{
        if (customer.getCustomerState() == CustomerState.USE || customer.getCustomerState() == CustomerState.OVERDUE) {
            CustomerOrder lastOrder = customerOrderRepository.findLastOrderOfCustomer(customer.getId());
            lastOrder.setReturnedDateTime(LocalDateTime.now());
            customer.completeReturnCup(0, CustomerState.NONE);
        } else {
            throw new NoCupsForReturnException();
        }
    }

    @Transactional
    public void cupStateRenewal(CustomerOrder customerOrder, Customer customer) {
        if (customerOrder.getReturnDateTime().isBefore(LocalDateTime.now())) {
            customer.setCustomerState(CustomerState.OVERDUE);
        }
    }
}

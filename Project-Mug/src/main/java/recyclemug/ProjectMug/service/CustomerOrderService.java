package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
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
        List<CustomerOrder> customerOrders = customerOrderRepository.findListOfCustomer(customer.getId());
        if (customerOrders.isEmpty()) {
            throw new NoCupsForReturnException();
        } else {
            Long customerCupId = customerOrders.get(0).getId();
            CustomerOrder customerOrder = customerOrderRepository.findById(customerCupId);
            Cup cup = customerOrder.getCup();
            int cupPrice = cup.getPrice();

            if (LocalDateTime.now().isBefore(customerOrder.getReturnDateTime())) {
                customer.completeReturnCup(cupPrice, CustomerState.NONE);
            } else {
                customer.completeReturnCup(cupPrice, CustomerState.OVERDUE);
            }
            customerOrder.setReturnedDateTime(LocalDateTime.now());

            List<PartnerReturn> partnerReturns = partnerReturnRepository.findByPartnerIdAndCupId(partner.getId(), cup.getId());

            if (partnerReturns.isEmpty()) {
                PartnerReturn partnerReturn = new PartnerReturn(partner, cup, 1);
                partnerReturnRepository.save(partnerReturn);
            } else {
                PartnerReturn partnerReturn = partnerReturns.get(0);
                partnerReturn.setReturnQuantity(partnerReturn.getReturnQuantity() + 1);
            }
        }
    }

    /**
     * Customer 가 컵을 파손, 분실하여 컵이 삭제되는 메서드
     * @param customerOrder
     */
    @Transactional
    public void cupRemoveOfCustomer(CustomerOrder customerOrder) {
        Customer customer = customerOrder.getCustomer();
        customer.setCustomerState(CustomerState.NONE);
        customerOrder.setReturnedDateTime(LocalDateTime.now());
    }
}

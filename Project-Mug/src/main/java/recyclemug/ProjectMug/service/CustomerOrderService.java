package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.CustomerState;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.CustomerStateNotAllowedException;
import recyclemug.ProjectMug.exception.NoCupsForReturnException;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.CustomerOrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

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
            partnerCup.setStockQuantity(partnerCup.getStockQuantity() - 1);
            customer.setPoint(customer.getPoint() - partnerCup.getCup().getPrice());
            customer.setCustomerState(CustomerState.USE);
            customerOrderRepository.saveCup(customerOrder);
        }
    }

    /**
     * Customer 가 컵 반납하는 메서드
     * returnedCupStatus 가 true 면 정상 반납, false 면 분실, 파손 반납
     * @param customer
     */
    @Transactional
    public void cupReturnOfCustomer(Customer customer, Partner partner, Boolean returnedCupStatus) {
        if (!returnedCupStatus) return;

        List<CustomerOrder> customerOrders = customer.getCustomerOrders();
        if (customerOrders.isEmpty()) {
            throw new NoCupsForReturnException();
        } else {
            Long customerCupId = customerOrders.get(customerOrders.size() - 1).getId();
            CustomerOrder customerOrder = customerOrderRepository.findById(customerCupId);

            if (LocalDateTime.now().isBefore(customerOrder.getReturnDateTime())) {
                customerOrder.setReturnedDateTime(LocalDateTime.now());
            } else {
                customerOrder.setReturnedDateTime(LocalDateTime.now());
                customer.setCustomerState(CustomerState.OVERDUE);
            }
            customer.setPoint(customer.getPoint() + customerOrder.getCup().getPrice());
        }
    }
}

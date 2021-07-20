package recyclemug.ProjectMug.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.CustomerCup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.CustomerState;
import recyclemug.ProjectMug.exception.CustomerStateNotAllowedException;
import recyclemug.ProjectMug.exception.NoCupsForReturnException;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.CustomerCupRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerCupService {

    private final CustomerCupRepository customerCupRepository;

    /**
     * Customer 의 Partner 에게 컵 주문시 메서드
     * @param customer
     * @param partnerCup
     * @throws NotEnoughPointException
     * @throws NotEnoughStockException
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
            CustomerCup customerCup = new CustomerCup(customer, partnerCup.getCup());
            partnerCup.setStockQuantity(partnerCup.getStockQuantity() - 1);
            customer.setPoint(customer.getPoint() - partnerCup.getCup().getPrice());
            customer.setCustomerState(CustomerState.USE);
            customerCupRepository.saveCup(customerCup);
        }
    }

    @Transactional
    public void cupReturnOfCustomer(Customer customer) {
        List<CustomerCup> customerCups = customer.getCustomerCups();
        if (customerCups.isEmpty()) {
            throw new NoCupsForReturnException();
        } else {
            Long customerCupId = customerCups.get(customerCups.size() - 1).getId();
            CustomerCup customerCup = customerCupRepository.findById(customerCupId);

            if (LocalDateTime.now().isBefore(customerCup.getReturnDateTime())) {
                customerCup.setReturnedDateTime(LocalDateTime.now());
            } else {
                customerCup.setReturnedDateTime(LocalDateTime.now());
                customer.setCustomerState(CustomerState.OVERDUE);
            }
        }
    }
}

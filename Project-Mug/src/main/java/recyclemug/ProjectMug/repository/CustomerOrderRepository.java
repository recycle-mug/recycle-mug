package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.user.Customer;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerOrderRepository {

    private final EntityManager em;

    public void saveCup(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    public CustomerOrder findById(Long id) {
        return em.find(CustomerOrder.class, id);
    }

    /**
     * Customer 의 CustomerOrder 을 rentDateTime 내림차순으로 전부 반환
     * @param customerId
     * @return
     */
    public List<CustomerOrder> findListOfCustomer(Long customerId) {
        return em.createQuery("select c.customerOrders from Customer c where c.id=:customerId order by c.rentDateTime desc", CustomerOrder.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public CustomerOrder findLastOrderOfCustomer(Long customerId) {
        String query = "SELECT o FROM CustomerOrder o INNER JOIN o.customer c WHERE c.id=:customerId ORDER BY o.rentDateTime DESC";
        return em.createQuery(query, CustomerOrder.class)
                .setParameter("customerId", customerId)
                .setMaxResults(1).getSingleResult();
    }
}
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
     * return last cup order of customer
     * @param customerId
     * @return
     */
    public CustomerOrder findLastOrderOfCustomer(Long customerId) {
        String query = "SELECT o FROM CustomerOrder o INNER JOIN o.customer c WHERE c.id=:customerId ORDER BY o.rentDateTime DESC";
        return em.createQuery(query, CustomerOrder.class)
                .setParameter("customerId", customerId)
                .setMaxResults(1).getSingleResult();
    }
}
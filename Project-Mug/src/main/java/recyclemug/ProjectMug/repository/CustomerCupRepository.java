package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CustomerCupRepository {

    private final EntityManager em;

    public void saveCup(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    public CustomerOrder findById(Long id) {
        return em.find(CustomerOrder.class, id);
    }
}

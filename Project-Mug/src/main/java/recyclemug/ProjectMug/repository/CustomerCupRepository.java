package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.CustomerCup;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CustomerCupRepository {

    private final EntityManager em;

    public void saveCup(CustomerCup customerCup) {
        em.persist(customerCup);
    }
}

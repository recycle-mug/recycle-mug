package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.ReturnedCup;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReturnedCupRepository {

    private final EntityManager em;

    public void save(ReturnedCup returnedCup) {
        em.persist(returnedCup);
    }

    public List<ReturnedCup> findAll() {
        return em.createQuery("SELECT rc FROM ReturnedCup rc", ReturnedCup.class).getResultList();
    }
}

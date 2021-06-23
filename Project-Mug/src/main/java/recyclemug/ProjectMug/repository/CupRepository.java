package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.Cup;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CupRepository {

    private final EntityManager em;

    public void save(Cup cup) {
        em.persist(cup);
    }

    public void remove(Cup cup) {
        em.remove(cup);
    }

    public List<Cup> findAllCup() {
        return em.createQuery("select c from Cup c", Cup.class).getResultList();
    }

    // 하나만 찾아오는 메서드들
    public List<Cup> findByCupName(String name) {
        return em.createQuery("select c from Cup c where c.name = :name", Cup.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Cup findByCupId(Long id) {
        return em.find(Cup.class, id);
    }
}

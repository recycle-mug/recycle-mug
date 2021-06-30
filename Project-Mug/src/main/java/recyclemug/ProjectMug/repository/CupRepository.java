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

    /**
     * findAllCups: 모든 컵 리스트를 JSON 형태로 반환
     * findByCupName: 이름 기반으로 컵 반환
     */
    public List<Cup> findAllCups() {
        return em.createQuery("select c from Cup c", Cup.class).getResultList();
    }

    public List<Cup> findByCupName(String name) {
        return em.createQuery("select c from Cup c where c.name = :name", Cup.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Cup findByCupId(Long id) {
        return em.find(Cup.class, id);
    }
}

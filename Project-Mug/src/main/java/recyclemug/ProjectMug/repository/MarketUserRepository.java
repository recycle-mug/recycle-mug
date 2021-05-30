package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.user.MarketUser;
import recyclemug.ProjectMug.domain.user.OrdinaryUser;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketUserRepository {

    private final EntityManager em;

    public void save(MarketUser marketUser){
        if (marketUser.getId() == null) {
            em.persist(marketUser);
        } else {
            em.merge(marketUser);
        }
    }

    public MarketUser findOne(Long id) {
        return em.find(MarketUser.class, id);
    }

    public List<MarketUser> findAll() {
        return em.createQuery("select u from MarketUser u", MarketUser.class)
                .getResultList();
    }

    public List<MarketUser> findByEmail(String email) {
        return em.createQuery("select u from MarketUser u where u.userEmail = :email", MarketUser.class)
                .setParameter("email", email)
                .getResultList();
    }
}

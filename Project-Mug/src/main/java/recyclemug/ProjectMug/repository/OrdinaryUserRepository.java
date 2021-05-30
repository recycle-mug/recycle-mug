package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.user.OrdinaryUser;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrdinaryUserRepository {

    private final EntityManager em;

    public void save(OrdinaryUser ordinaryUser){
        if (ordinaryUser.getId() == null) {
            em.persist(ordinaryUser);
        } else {
            em.merge(ordinaryUser);
        }
    }

    public OrdinaryUser findOne(Long id) {
        return em.find(OrdinaryUser.class, id);
    }

    public List<OrdinaryUser> findAll() {
        return em.createQuery("select u from OrdinaryUser u", OrdinaryUser.class)
                .getResultList();
    }

    public List<OrdinaryUser> findByEmail(String email){
        return em.createQuery("select u from OrdinaryUser u where u.userEmail = :email", OrdinaryUser.class)
                .setParameter("email", email)
                .getResultList();
    }
}

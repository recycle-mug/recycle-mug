package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.user.Partner;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PartnerRepository {

    private final EntityManager em;

    public void save(Partner partner){
        if (partner.getId() == null) {
            em.persist(partner);
        }
    }

    public Partner findOne(Long id) {
        return em.find(Partner.class, id);
    }

    public List<Partner> findAll() {
        return em.createQuery("select p from Partner p", Partner.class)
                .getResultList();
    }

    public List<Partner> findByEmail(String email) {
        return em.createQuery("select p from Partner p where p.email = :email", Partner.class)
                .setParameter("email", email)
                .getResultList();
    }
}

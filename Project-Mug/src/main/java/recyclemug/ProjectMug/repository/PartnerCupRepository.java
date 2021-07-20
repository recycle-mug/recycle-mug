package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PartnerCupRepository {

    private final EntityManager em;

    public void saveCup(PartnerCup partnerCup) {
        em.persist(partnerCup);
    }

    public PartnerCup findById(Long id) {
        return em.find(PartnerCup.class, id);
    }

    public PartnerCup findByPartnerIdAndCupId(Long partnerId, Long cupId) {
        return em.createQuery("select p from PartnerCup p where p.partner_id=:partnerId and p.cupId=:cupId", PartnerCup.class)
                .setParameter("partnerId", partnerId)
                .setParameter("cupId", cupId).getSingleResult();
    }

}

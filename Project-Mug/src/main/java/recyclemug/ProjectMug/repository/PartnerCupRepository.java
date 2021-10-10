package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.cup.PartnerOrder;
import recyclemug.ProjectMug.domain.user.Partner;

import javax.persistence.EntityManager;
import java.util.List;

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

    /**
     * Partner 가 가지고있는 Cup list 반환
     * @return
     */
    public List<PartnerCup> findCupOfPartner(Long partnerId) {
        return em.createQuery("select p.partnerCups from Partner p join fetch p.partnerCups WHERE p.id=:partnerId", PartnerCup.class)
                .setParameter("partnerId", partnerId)
                .getResultList();
//        return em.createQuery("select pc from PartnerCup pc join fetch p.partner p WHERE p.id=:partnerId", PartnerCup.class)
//                .setParameter("partnerId", partnerId)
//                .getResultList();
    }

    /**
     * partner 가 가지고있는 partnerCup 를 partnerId 와 cupId 로 찾는다.
     * @param partnerId
     * @param cupId
     * @return
     */
    public List<PartnerCup> findByPartnerIdAndCupId(Long partnerId, Long cupId) {
        String query = "SELECT pc FROM PartnerCup pc JOIN pc.cup c JOIN pc.partner p WHERE c.id=:cupId and p.id=:partnerId";
        return em.createQuery(query, PartnerCup.class)
                .setParameter("partnerId", partnerId)
                .setParameter("cupId", cupId).getResultList();
    }
}

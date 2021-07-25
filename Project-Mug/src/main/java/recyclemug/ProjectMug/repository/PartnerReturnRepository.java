package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.PartnerReturn;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PartnerReturnRepository {

    private final EntityManager em;

    public void save(PartnerReturn partnerReturn) {
        em.persist(partnerReturn);
    }

    /**
     * for: id 를 통해서 PartnerReturn 을 찾아주는 메서드
     * when: ?
     * @param id
     * @return
     */
    public PartnerReturn findById(Long id) {
        return em.find(PartnerReturn.class, id);
    }

    /**
     * for: 모든 PartnerReturn 들을 반환
     * when: Admin 에서 사용 가능성 있음
     * @return
     */
    public List<PartnerReturn> findAll() {
        return em.createQuery("SELECT pr FROM PartnerReturn", PartnerReturn.class).getResultList();
    }

    /**
     * for: 특정 파트너에 대해서 Id 로 모든 회수해야할 컵들의 항목들을 반환하는 메서드
     * when: Admin 이 Partner 가게에 가서 회수하는 상황
     * @param partnerId
     * @return
     */
    public List<PartnerReturn> findByPartnerId(Long partnerId) {
        return em.createQuery("SELECT pr FROM PartnerReturn pr JOIN pr.partner p WHERE p.id=:partnerId", PartnerReturn.class).getResultList();
    }

    /**
     * 수정 가능성 있음 3중 JOIN 문 이기 때문에
     * when: Customer 가 반납완료해서 PartnerReturn 을 추가해야 하는데 이 때 이미 먼저 반환된 컵이 있을때와 없을때를 찾기 위해
     * @param partnerId
     * @param cupId
     * @return
     */
    public List<PartnerReturn> findByPartnerIdAndCupId(Long partnerId, Long cupId) {
        String query = "SELECT pr FROM PartnerReturn JOIN pr.partner p JOIN pr.cup c WHERE p.id=:partnerId and c.id=:cupId";
        return em.createQuery(query, PartnerReturn.class).getResultList();
    }

    /**
     * Partner Return 삭제 메서드
     * @param partnerReturn
     */
    public void remove(PartnerReturn partnerReturn) {
        em.remove(partnerReturn);
    }
}

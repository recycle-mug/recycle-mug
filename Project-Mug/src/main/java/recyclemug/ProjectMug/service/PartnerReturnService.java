package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerReturn;
import recyclemug.ProjectMug.domain.cup.ReturnedCup;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.repository.PartnerReturnRepository;
import recyclemug.ProjectMug.repository.ReturnedCupRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartnerReturnService {

    private final PartnerReturnRepository partnerReturnRepository;
    private final ReturnedCupRepository returnedCupRepository;

    /**
     * For: Admin 이 Partner 가게에 와서 컵을 회수할때 컵 종류마다 회수버튼 처리를 통해 회수하는 메서드
     * when: Admin 이 PartnerReturn 항목에 대해 회수 완료 버튼을 누를 때
     * @param partnerReturnId
     */
    @Transactional
    public void completeReturn(Long partnerReturnId) {
        PartnerReturn partnerReturn = partnerReturnRepository.findById(partnerReturnId);
        Cup cup = partnerReturn.getCup();
        Partner partner = partnerReturn.getPartner();
        returnedCupRepository.save(new ReturnedCup(partner, cup, partnerReturn.getReturnQuantity()));
        cup.setPrice(partnerReturn.getReturnQuantity());
        partnerReturnRepository.remove(partnerReturn);
    }
}

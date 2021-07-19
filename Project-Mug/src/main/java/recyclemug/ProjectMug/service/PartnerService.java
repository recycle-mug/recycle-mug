package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Authority;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.exception.NotEnoughPointException;
import recyclemug.ProjectMug.exception.NotEnoughStockException;
import recyclemug.ProjectMug.repository.PartnerCupRepository;
import recyclemug.ProjectMug.repository.PartnerRepository;
import recyclemug.ProjectMug.repository.UserRepositoryInterface;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final UserRepositoryInterface userRepositoryInterface;
    private final PasswordEncoder passwordEncoder;
    private final PartnerCupRepository partnerCupRepository;

    @Transactional
    public Long join(Partner partner) {
        validateDuplicate(partner); // 중복 회원 체크
        validatePassword(partner);

        Authority authority = Authority.builder().authorityName("ROLE_PARTNER").build();

        partner.setPassword("{noop}" + partner.getPassword());
        partner.setActivated(true);
        partner.setAuthorities(Collections.singleton(authority));

        partnerRepository.save(partner);
        return partner.getId();
    }

    /**
     * 파트너가 컵을 주문하는 메서드
     *
     * @param cup
     * @param partner
     * @param orderQuantity
     */
    @Transactional
    public void cupOrderOfPartner(Cup cup, Partner partner, int orderQuantity) throws NotEnoughPointException, NotEnoughStockException {
        if (cup.getStockQuantity() < orderQuantity) {
            throw new NotEnoughStockException();
        } else if (cup.getPrice() * orderQuantity > partner.getPoint()) {
            throw new NotEnoughPointException();
        } else {
            PartnerCup partnerCup = new PartnerCup(partner, cup, orderQuantity);
            partner.setPoint(partner.getPoint() - cup.getPrice() * orderQuantity);
            cup.setStockQuantity(cup.getStockQuantity() - orderQuantity);
            partnerCupRepository.saveCup(partnerCup);
        }
    }

    public Partner findById(Long id) {
        return partnerRepository.findOne(id);
    }

    public List<Partner> findPartners() {
        return partnerRepository.findAll();
    }

    public List<Partner> findByEmail(String email) {
        return partnerRepository.findByEmail(email);
    }

    // 중복되는 이메일을 가지는 회원이 있는지를 판별
    public void validateDuplicate(Partner partner) {
        List<Partner> findUsers = partnerRepository.findByEmail(partner.getEmail());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 패스워드 체크 8자리 이상 20자리 이하 대문자, 소문자, 숫자, 특수문자 중 3가지 사용
    public void validatePassword(Partner partner) {
        String pw = partner.getPassword();
        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~$@$!%*#?&*])[A-Za-z[0-9]~$@$!%*#?&*]{8,20}$"; // 영문, 숫자, 특수문자
        Matcher match;

        match = Pattern.compile(pattern).matcher(pw);
        if(!match.find()) {
            throw new IllegalStateException("비밀번호를 양식에 맞게 작성해야 합니다");
        }
    }
}

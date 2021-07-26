package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.data.PartnerModifyDTO;
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

    /**
     * partner 회원가입 메서드
     * @param partner
     * @return
     */
    @Transactional
    public Long join(Partner partner) throws RuntimeException{
        if (validateDuplicate(partner.getEmail()) && validatePassword(partner.getPassword())) {
            Authority authority = Authority.builder().authorityName("ROLE_PARTNER").build();

            partner.setPassword("{noop}" + partner.getPassword());
            partner.setActivated(true);
            partner.setAuthorities(Collections.singleton(authority));

            partnerRepository.save(partner);
            return partner.getId();
        } else {
            throw new RuntimeException("You need to check ID or PW");
        }

    }

    /**
     * partner 수정 메서드 : Partner 객체에 PartnerDTO 의 정보를 변경감지로 적용
     * @param partner
     * @param partnerDTO
     */
    @Transactional
    public void modifyPartnerInfo(Partner partner, PartnerModifyDTO partnerDTO) {
        if (validatePassword(partner.getPassword())) {
            partner.setPassword("{noop}" + partnerDTO.getPassword());
            partner.setPhoneNumber(partnerDTO.getPhoneNumber());
            partner.setNickname(partnerDTO.getNickname());
            partner.setZipcode(partnerDTO.getZipcode());
            partner.setDetailAddress(partnerDTO.getDetailAddress());
            partner.setRegistrationNumber(partnerDTO.getRegistrationNumber());
            partner.setBusinessName(partnerDTO.getBusinessName());
        } else {
            throw new RuntimeException("Wrong type PW");
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
    public boolean validateDuplicate(String email) {
        List<Partner> findUsers = partnerRepository.findByEmail(email);
        return findUsers.isEmpty();
    }

    // 패스워드 체크 8자리 이상 20자리 이하 대문자, 소문자, 숫자, 특수문자 중 3가지 사용
    public boolean validatePassword(String pw) {
        String pattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~$@$!%*#?&*])[A-Za-z[0-9]~$@$!%*#?&*]{8,20}$"; // 영문, 숫자, 특수문자
        Matcher match;

        match = Pattern.compile(pattern).matcher(pw);
        return match.find();
    }
}

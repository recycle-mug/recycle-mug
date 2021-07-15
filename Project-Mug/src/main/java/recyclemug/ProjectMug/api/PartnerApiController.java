package recyclemug.ProjectMug.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreatePartnerRequest;
import recyclemug.ProjectMug.data.CreatePartnerJoinResponse;
import recyclemug.ProjectMug.data.CreatePartnerResponse;
import recyclemug.ProjectMug.domain.cup.PartnerCup;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.service.PartnerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerApiController {

    private final PartnerService partnerService;

    @PostMapping("/join/partner")
    public CreatePartnerJoinResponse savePartner(@RequestBody @Valid CreatePartnerRequest request, HttpServletRequest httpServletRequest) {
        String pictureAddress = httpServletRequest.getServletContext().getRealPath("/images/users/default_user.jpg");

        try {
            Partner partner = Partner.createPartner(request.getEmail(), request.getPw(), request.getTel(),
                    request.getAddress_num(), request.getAddress() + request.getAddress_detail());
            partner.setProfilePictureAddress(pictureAddress);
            partnerService.join(partner);
            return new CreatePartnerJoinResponse("success", "회원가입에 성공했습니다.");
        } catch (IllegalStateException e) {
            log.error("FAIL TO JOIN PARTNER");
        }
        return null;
    }

    @GetMapping("/partner/{partnerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PARTNER')")
    @ResponseBody
    public CreatePartnerResponse getPartner(@PathVariable Long partnerId) {
        try {
            Partner partner = partnerService.findById(partnerId);
            log.info("get Partner success");
            return createPartnerResponse(partner);
        } catch (NullPointerException e) {
            log.error("DB에 없는 ID Partner 조회 에러");
        } catch (FileNotFoundException e) {
            log.error("이미지가 없습니다.");
        } catch (IOException e) {
            log.error("Image to byte Exception: image bytes 생성 실패");
        }
        return null;
    }

    public CreatePartnerResponse createPartnerResponse(Partner partner) throws IOException {
        CreatePartnerResponse response = CreatePartnerResponse.builder()
                .id(partner.getId()).email(partner.getEmail())
                .point(partner.getPoint()).nickname(partner.getNickname())
                .businessName(partner.getBusinessName()).zipcode(partner.getZipcode())
                .detailAddress(partner.getDetailAddress())
                .detailAddress(partner.getDetailAddress())
                .partnerCups(partner.getPartnerCups())
                .build();

        FileInputStream imageStream = new FileInputStream(partner.getProfilePictureAddress());
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        response.setProfilePicture(image);
        return response;
    }
}

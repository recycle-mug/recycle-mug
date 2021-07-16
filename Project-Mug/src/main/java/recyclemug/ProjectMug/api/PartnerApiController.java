package recyclemug.ProjectMug.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.data.CreateJoinResponse;
import recyclemug.ProjectMug.data.CreatePartnerRequest;
import recyclemug.ProjectMug.data.CreatePartnerResponse;
import recyclemug.ProjectMug.domain.user.Partner;
import recyclemug.ProjectMug.service.PartnerService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PartnerApiController {

    private final PartnerService partnerService;

    @PostMapping("/join/partner")
    @ResponseBody
    public ResponseEntity<CreateJoinResponse> savePartner(@RequestBody @Valid CreatePartnerRequest request, HttpServletRequest httpServletRequest) {
        String pictureAddress = httpServletRequest.getServletContext().getRealPath("/images/users/default_user.jpg");

        try {
            Partner partner = Partner.createPartner(request.getEmail(), request.getPassword(), request.getPhoneNumber(),
                    request.getZipcode(), request.getAddress() + request.getDetailAddress());
            partner.setProfilePictureAddress(pictureAddress);
            partnerService.join(partner);
            return ResponseEntity.ok(new CreateJoinResponse("success", "회원가입에 성공했습니다."));
        } catch (IllegalStateException e) {
            log.error("FAIL TO JOIN PARTNER");
        } catch (Exception e) {
            log.error("FAIL");
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
            log.error("DB에 없는 Partner id 조회");
        } catch (FileNotFoundException e) {
            log.error("No image for partner id: " + partnerId);
        } catch (IOException e) {
            log.error("IOException");
        }
        return null;
    }

    public CreatePartnerResponse createPartnerResponse(Partner partner) throws IOException {
        CreatePartnerResponse response = CreatePartnerResponse.builder()
                .id(partner.getId())
                .email(partner.getEmail())
                .phoneNumber(partner.getPhoneNumber())
                .point(partner.getPoint())
                .nickname(partner.getNickname())
                .businessName(partner.getBusinessName())
                .zipcode(partner.getZipcode())
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

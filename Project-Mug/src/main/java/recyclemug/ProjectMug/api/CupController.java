package recyclemug.ProjectMug.api;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.repository.CupRepository;
import recyclemug.ProjectMug.service.CupService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CupController {

    private final CupService cupService;
    private final CupRepository cupRepository;

    @PostMapping("/cup/add")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void saveCup(@RequestParam("file") MultipartFile file,
                        @ModelAttribute("request") CreateCupRequest request) {
        // Image File 처리 original File name 을 만들어 새로운 src 주소를 만들어낸다.
        Date date = new Date();
        StringBuilder sb = new StringBuilder();
        String picturePathName = "/Users/hanjaeseo/images/cups/";

        // file image 가 없을 경우
        if (file.isEmpty()) {
            sb.append("none");
        } else {
            sb.append(date.getTime());
            sb.append(file.getOriginalFilename());
        }

        picturePathName += sb.toString();

        if (!file.isEmpty()) {
            File dest = new File(picturePathName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Cup cup = Cup.createCup(request.name, request.price, picturePathName);
        cupService.addCup(cup);
    }

    @DeleteMapping("/cup/remove/{cupId}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void removeCup(@PathVariable Long cupId) {
        try {
            cupService.removeCup(cupId);
        } catch (IllegalStateException e) {
            log.error("컵이 존재하지 않는 상태에서 삭제하려 합니다.");
        }
    }

    @GetMapping("/cup/list")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public List<Cup> findAllCups() {
        return cupRepository.findAllCups();
    }

    @GetMapping("/cup/{cupId}")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public CreateCupResponse findCup(@PathVariable Long cupId) throws IOException {
        Cup cup = cupRepository.findByCupId(cupId);
        FileInputStream imageStream = new FileInputStream(cup.getProfilePicture());
        byte[] imageByteArray = imageStream.readAllBytes();
        imageStream.close();

        return new CreateCupResponse(cup.getName(), cup.getPrice(), imageByteArray);
    }

    @Data
    static class CreateCupRequest {
        private String name;
        private int price;
    }

    @Data
    static class CreateCupResponse {
        private String name;
        private int price;
        private byte[] image;

        public CreateCupResponse(String name, int price, byte[] image) {
            this.name = name;
            this.price = price;
            this.image = image;
        }
    }

}
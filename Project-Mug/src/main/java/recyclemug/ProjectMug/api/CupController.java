package recyclemug.ProjectMug.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.service.CupService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CupController {

    private final CupService cupService;

    @PostMapping("/cup/add")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void saveCup(@RequestBody @Valid CreateCupRequest request) {
        Cup cup = Cup.createCup(request.name, request.price, request.profilePicture);
        cupService.addCup(cup);
    }

    @DeleteMapping("/cup/remove")
    @PostAuthorize("hasAnyRole('ADMIN')")
    public void removeCup(@RequestBody @Valid CreateCupRequest request) {
        String cupName = request.getName();
        cupService.removeCup(cupName);
    }

    @Data
    static class CreateCupRequest {
        private String name;
        private int price;
        private String profilePicture;
    }
}

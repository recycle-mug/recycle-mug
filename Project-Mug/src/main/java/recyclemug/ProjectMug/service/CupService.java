package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.repository.CupRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CupService {

    private final CupRepository cupRepository;

    @Transactional
    public void addCup(Cup cup) {
        cupRepository.save(cup);
    }

//    @Transactional
//    public void removeCup(Cup cup) {
//
//    }
}

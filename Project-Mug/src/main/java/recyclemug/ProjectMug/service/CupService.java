package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.repository.CupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CupService {

    private final CupRepository cupRepository;

    @Transactional
    public void addCup(Cup cup) {
        validateDuplicate(cup);
        cupRepository.save(cup);
    }

    @Transactional
    public void removeCup(String name){
        List<Cup> cups = cupRepository.findByCupName(name);
        if (!cups.isEmpty()) {
            for (Cup cup : cups) {
                cupRepository.remove(cup);
            }
        } else {
            throw new IllegalStateException("지우려는 컵이 존재하지 않습니다.");
        }
    }

    // 중복되는 컵 탐색
    public void validateDuplicate(Cup cup) {
        List<Cup> cups = cupRepository.findByCupName(cup.getName());
        if (!cups.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 컵입니다.");
        }
    }
}

package recyclemug.ProjectMug.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.user.User;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    @Transactional
    public void updateLastLogin(User user) {
        user.setLastLoginDateTIme(LocalDateTime.now());
    }
}

package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.user.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public List<User> findAllUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

}

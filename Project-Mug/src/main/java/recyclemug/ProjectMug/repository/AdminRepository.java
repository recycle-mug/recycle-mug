package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.user.Admin;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public Admin findByEmail(String email) {
        return em.createQuery("select a from Admin a where a.email = :email", Admin.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}

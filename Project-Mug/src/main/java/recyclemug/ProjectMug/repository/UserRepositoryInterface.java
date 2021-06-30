package recyclemug.ProjectMug.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import recyclemug.ProjectMug.domain.user.Customer;
import recyclemug.ProjectMug.domain.user.User;

import java.util.Optional;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
    //해당 쿼리가 수행될때 Eager 조회로 authorities 정보를 같이 가져오게함
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmail(String email);
}

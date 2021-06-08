package recyclemug.ProjectMug.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import recyclemug.ProjectMug.domain.user.Customer;

import java.util.Optional;

public interface CustomerRepositoryInterface extends JpaRepository<Customer, Long> {
    //해당 쿼리가 수행될때 Eager 조회로 authorities 정보를 같이 가져오게함
    @EntityGraph(attributePaths = "authorities")
    Optional<Customer> findOneWithAuthoritiesByEmail(String email);
}

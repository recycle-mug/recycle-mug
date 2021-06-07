package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.domain.user.Customer;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerRepository {

    private final EntityManager em;

    public void save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        }
    }

    public Customer findOne(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createQuery("select p from Customer p", Customer.class)
                .getResultList();
    }

    public List<Customer> findByEmail(String email) {
        return em.createQuery("select p from Customer p where p.email = :email", Customer.class)
                .setParameter("email", email)
                .getResultList();
    }
}

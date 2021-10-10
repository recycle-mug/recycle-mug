package recyclemug.ProjectMug.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import recyclemug.ProjectMug.data.CustomerOrderIdResponse;
import recyclemug.ProjectMug.domain.cup.Cup;
import recyclemug.ProjectMug.domain.cup.CustomerOrder;
import recyclemug.ProjectMug.domain.user.Customer;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomerOrderRepository {

    private final EntityManager em;

    public void saveCup(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    public CustomerOrder findById(Long id) {
        return em.find(CustomerOrder.class, id);
    }

    /**
     * return last cup order of customer
     * @param customerId
     * @return
     */
    public CustomerOrder findLastOrderOfCustomer(Long customerId) {
        String query = "SELECT o FROM CustomerOrder o INNER JOIN o.customer c WHERE c.id=:customerId ORDER BY o.rentDateTime DESC";
        return em.createQuery(query, CustomerOrder.class)
                .setParameter("customerId", customerId)
                .setMaxResults(1).getSingleResult();
    }

    public List<CustomerOrder> findAll() {
        String query = "SELECT co FROM CustomerOrder co";
        return em.createQuery(query, CustomerOrder.class).getResultList();
    }

    /**
     * customerId number 를 기준으로 지금까지 빌렸던 컵 리스트를 가져오는 메서드
     * Fetch Join 최적화 테스트
     * @param customerId
     * @return
     * @throws IOException
     */
    public List<CustomerOrderIdResponse> findByCustomerId(Long customerId) throws IOException{
        String query = "SELECT o FROM CustomerOrder o INNER JOIN o.customer c WHERE c.id=:customerId ORDER BY o.rentDateTime DESC";
        ArrayList<CustomerOrderIdResponse> customerOrders = new ArrayList<>();
        Cup cup;
        for (CustomerOrder customerOrder : em.createQuery(query, CustomerOrder.class).setParameter("customerId", customerId).getResultList()) {
            cup = customerOrder.getCup();
            CustomerOrderIdResponse order = CustomerOrderIdResponse.builder()
                    .partnerName(customerOrder.getPartner().getNickname())
                    .cupName(cup.getName())
                    .cupImage(findPicture(cup.getProfilePictureAddress()))
                    .rentDateTime(customerOrder.getRentDateTime())
                    .returnDateTime(customerOrder.getReturnDateTime())
                    .returnedDateTime(customerOrder.getReturnedDateTime())
                    .build();
            customerOrders.add(order);
        }
        return customerOrders;
    }

    public byte[] findPicture(String profilePictureAddress) throws IOException {
        FileInputStream imageStream = new FileInputStream(profilePictureAddress);
        byte[] image = imageStream.readAllBytes();
        imageStream.close();
        return image;
    }
}
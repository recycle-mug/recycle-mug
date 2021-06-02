package recyclemug.ProjectMug.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.user.Customer;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired CustomerService customerService;


    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // given
        Customer customer = Customer.createCustomer("wotj7687@naver.com",
                "A1351611a~",
                "010-4321-1447");
        customerService.join(customer);
        // when

        // then
    }
}
package recyclemug.ProjectMug.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.user.Customer;

import java.util.List;

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

    @Test
    public void 중복체크() throws Exception {
        // given
        Customer customer1 = Customer.createCustomer("wotj7687@naver.com", "A1351611a~",
                "010-4321-1447");
        customerService.join(customer1);
        // when
        assertThrows(IllegalStateException.class, ()-> {
            Customer customer2 = Customer.createCustomer("wotj7687@naver.com", "A1351611a~",
                    "010-4321-1447");
            customerService.join(customer2);
        });
        // then
    }

    @Test
    public void 이메일로아이디찾기() throws Exception {
        // given
        Customer customer1 = Customer.createCustomer("wotj7687@naver.com", "A1351611a~",
                "010-4321-1447");
        customerService.join(customer1);
        // when
        List<Customer> customers = customerService.findByEmail(customer1.getEmail());
        Customer findIdCustomer = customerService.findById(1L);
        Customer findEmailCustomer = customers.get(0);
        // then
        // emil 로 찾아온 customer 와 id 로 찾아온 customer 가 같은 customer 인지 검증
        Assertions.assertThat(findIdCustomer).isEqualTo(findEmailCustomer);
    }
}
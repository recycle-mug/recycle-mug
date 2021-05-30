package recyclemug.ProjectMug.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import recyclemug.ProjectMug.domain.user.MarketUser;
import recyclemug.ProjectMug.repository.MarketUserRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MarketUserServiceTest {
    @Autowired
    MarketUserRepository marketUserRepository;

    @Autowired
    MarketUserService userService;

    @Test
    public void 회원가입() throws Exception {
        // given
        MarketUser marketUser = new MarketUser();
        marketUser.setMarketName("카페 1리터");
        marketUser.setUserEmail("wotj7687@naver.com");
        marketUser.setPassword("A1351611a~");

        MarketUser marketUser1 = new MarketUser();
        marketUser1.setMarketName("카페 1리터");
        marketUser1.setUserEmail("wotj7687@naver.com");
        marketUser1.setPassword("A1351611a~");
        // when
        userService.join(marketUser);

        Assertions.assertThrows(IllegalStateException.class, () ->{
            userService.join(marketUser1);
        });

        // then
    }

    @Test
    public void 비밀번호_체크() throws Exception {
        // given
        MarketUser marketUser = new MarketUser();
        marketUser.setMarketName("카페 1리터");
        marketUser.setUserEmail("wotj7687@naver.com");
        marketUser.setPassword("A1351611a~");
        userService.join(marketUser);
        // when
        Assertions.assertThrows(IllegalStateException.class, ()->{
            userService.join(marketUser);
        });
        // then
    }
}
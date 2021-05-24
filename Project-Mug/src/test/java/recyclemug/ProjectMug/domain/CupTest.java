package recyclemug.ProjectMug.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CupTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCup() {
        Store store = new Store();
        store.setStoreName("카페일리터");
        store.setLocate(new Locate(32L, 64L));
        em.persist(store);

        Cup cup = new Cup();
        cup.setLentDate(LocalDateTime.now());
        cup.setStore(store);
        em.persist(cup);

        Cup cup2 = new Cup();
        cup2.setLentDate(LocalDateTime.now());
        cup2.setStore(store);
        em.persist(cup2);

        em.flush();
        em.clear();

        Store store2 = em.find(Store.class, store.getId());
        System.out.println(store2.getStoreName());
        System.out.println("----------------------");

        for (Cup c : store2.getCupList()) {
            System.out.println("A Cup is " + c);
        }
    }
}
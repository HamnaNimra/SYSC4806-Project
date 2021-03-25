package Sysc4806Group.demo.entityTest;

import Sysc4806Group.demo.entities.User;
import Sysc4806Group.demo.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserIntegrationTest {
    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.save(new User("Hamna", "Nimra", "hamnanimra@github.com", "easypassword?" , User.Role.CUSTOMER));
        repository.save(new User("Donald", "Duck", "dd@disney.com", "Waltz", User.Role.CUSTOMER));
        repository.save(new User("Mickey", "Mouse", "mm@disney.com", "mmclubhouse", User.Role.OWNER));
    }

    @Test
    public void findByEmail() {
        User user = repository.findByEmail("dd@disney.com");
        Assert.assertEquals("Donald", user.getFirstName());
        Assert.assertEquals("Duck", user.getLastName());
        Assert.assertEquals(User.Role.CUSTOMER, user.getRole());
    }

}

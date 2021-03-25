package Sysc4806Group.demo.entityTest;
import Sysc4806Group.demo.entities.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.user= new User("Hamna", "Nimra", "hamnanimra@github.com", "easypassword?" , User.Role.CUSTOMER);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getFirstName(){
        assertEquals(user.getFirstName(),"Hamna");
    }
    @org.junit.jupiter.api.Test
    void setFirstName(){
        user.setFirstName("Babak");
        assertEquals(user.getFirstName(),"Babak");
    }
    @org.junit.jupiter.api.Test
    void getLastName(){
        assertEquals(user.getLastName(),"Nimra");
    }
    @org.junit.jupiter.api.Test
    void setLastName(){
        user.setLastName("Esfandiari");
        assertEquals(user.getLastName(),"Esfandiari");
    }
    @org.junit.jupiter.api.Test
    void getEmail(){
        assertEquals(user.getEmail(),"hamnanimra@github.com");
    }
    @org.junit.jupiter.api.Test
    void setEmail(){
        user.setEmail("hamna@carleton.ca");
        assertEquals(user.getEmail(),"hamna@carleton.ca");
    }
    @org.junit.jupiter.api.Test
    void getPassword(){
        assertEquals(user.getPassword(),"easypassword?");
    }
    @org.junit.jupiter.api.Test
    void setPassword(){
        user.setPassword("N0tE@sy2Guess");
        assertEquals(user.getPassword(),"N0tE@sy2Guess");
    }
    @org.junit.jupiter.api.Test
    void getRole(){
        assertEquals(user.getRole(),User.Role.CUSTOMER);
    }
    @org.junit.jupiter.api.Test
    void setRole(){
        user.setRole(User.Role.OWNER);
        assertEquals(user.getRole(),User.Role.OWNER);
    }

}

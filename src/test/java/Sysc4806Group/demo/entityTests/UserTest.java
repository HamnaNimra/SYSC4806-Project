package Sysc4806Group.demo.entityTests;

import Sysc4806Group.demo.entities.Role;
import Sysc4806Group.demo.entities.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;
    Set<Role> roles = new HashSet<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.user = new User( "10" ,"Hamna", "Nimra", "hamnanimra@github.com", "easypassword?");
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
    void getPassword(){
        assertEquals(user.getPassword(),"easypassword?");
    }
    @org.junit.jupiter.api.Test
    void setPassword(){
        user.setPassword("N0tE@sy2Guess");
        assertEquals(user.getPassword(),"N0tE@sy2Guess");
    }
}
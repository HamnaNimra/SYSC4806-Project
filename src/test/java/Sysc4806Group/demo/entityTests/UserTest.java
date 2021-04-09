package Sysc4806Group.demo.entityTests;

import Sysc4806Group.demo.entities.Role;
import Sysc4806Group.demo.entities.User;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;
    Set<Role> roles = new HashSet<>();
    private Object Role;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.user = new User( "10" ,"Hamna", "Nimra", "hamnanimra@github.com", "easypassword?", "ROLE_ADMIN");
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

    @org.junit.jupiter.api.Test
    void notPassword(){
        assertNotEquals(user.getPassword(),"hardPass?");
    }

    @org.junit.jupiter.api.Test
    void getUserRole(){
        assertEquals(user.getUserRole(),"ROLE_ADMIN");
    }
    @org.junit.jupiter.api.Test
    void setUserRole(){
        user.setUserRole("ROLE_USER");
        assertEquals(user.getUserRole(),"ROLE_USER");
    }

    @org.junit.jupiter.api.Test
    void notUserRole(){
        user.setUserRole("ROLE_USER");
        assertNotEquals(user.getUserRole(),"ROLE_ADMIN");
    }

}
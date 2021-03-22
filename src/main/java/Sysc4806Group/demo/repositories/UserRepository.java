package Sysc4806Group.demo.repositories;

import Sysc4806Group.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "User", path = "users")
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);
}

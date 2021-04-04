package Sysc4806Group.demo.repositories;

import java.util.Optional;

import Sysc4806Group.demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.Roles name);
}
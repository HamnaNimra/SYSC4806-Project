package Sysc4806Group.demo.repositories;

import Sysc4806Group.demo.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {

}

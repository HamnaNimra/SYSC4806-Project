package Sysc4806Group.demo.repositories;

import Sysc4806Group.demo.entities.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoreRepository extends JpaRepository<BookStore, Long> {
}

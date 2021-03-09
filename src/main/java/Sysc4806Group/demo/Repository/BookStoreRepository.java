package Sysc4806Group.demo.Repository;

import Sysc4806Group.demo.Entity.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoreRepository extends JpaRepository<BookStore, Long> {
}

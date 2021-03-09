package Sysc4806Group.demo.Repository;

import Sysc4806Group.demo.Entity.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

interface BookStoreRepository extends JpaRepository<BookStore, Long> {
}

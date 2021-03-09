package Sysc4806Group.demo.Repository;

import Sysc4806Group.demo.Entity.Book;
import Sysc4806Group.demo.Entity.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book, Long> {
}

package Sysc4806Group.demo.repositories;

import Sysc4806Group.demo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
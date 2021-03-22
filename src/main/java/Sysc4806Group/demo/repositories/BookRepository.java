package Sysc4806Group.demo.repositories;

import Sysc4806Group.demo.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByTitleContaining(String title);
    List<Book> findByTitleContains(String title);
    List<Book> findByTitleIsContaining(String title);
    List<Book> findByTitleContainingIgnoreCase(String title);
}
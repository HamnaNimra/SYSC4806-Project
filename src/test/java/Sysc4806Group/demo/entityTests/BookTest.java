package Sysc4806Group.demo.entityTests;

import Sysc4806Group.demo.entities.Book;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    Book book;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        this.book = new Book( "123456", "XYZ Book", "Random Author", "Nelson", "google.com/images", 15);
    }

    @org.junit.jupiter.api.Test
    void getIsbn(){
        assertEquals(book.getIsbn(), "123456");
    }
    @org.junit.jupiter.api.Test
    void setIsbn(){
        book.setIsbn("000456");
        assertEquals(book.getIsbn(), "000456");
        assertNotEquals(book.getIsbn(), "absy932");
    }

    @org.junit.jupiter.api.Test
    void notIsbn(){
        book.setIsbn("000456");
        assertNotEquals(book.getIsbn(), "absy932");
    }

    @org.junit.jupiter.api.Test
    void getTitle(){
        assertEquals(book.getTitle(), "XYZ Book");
        assertNotEquals(book.getTitle(), "My First Book");
    }

    @org.junit.jupiter.api.Test
    void notTitle(){
        assertNotEquals(book.getTitle(), "My First Book");
        assertNotEquals(book.getTitle(), "My Second Book");
    }

    @org.junit.jupiter.api.Test
    void setTitle(){
        book.setTitle("My First Book");
        assertEquals(book.getTitle(), "My First Book");
    }

    @org.junit.jupiter.api.Test
    void getAuthor(){
        assertNotEquals(book.getAuthor(), "Known Author");
        assertEquals(book.getAuthor(), "Random Author");
    }

    @org.junit.jupiter.api.Test
    void setAuthor(){
        book.setAuthor("Hamna Nimra");
        assertEquals(book.getAuthor(), "Hamna Nimra");
    }

    @org.junit.jupiter.api.Test
    void getPublisher(){
        assertEquals(book.getPublisher(), "Nelson");
    }
    @org.junit.jupiter.api.Test
    void setPublisher(){
        book.setPublisher("Pearson");
        assertEquals(book.getPublisher(), "Pearson");
    }

    @org.junit.jupiter.api.Test
    void getPictureUrl(){
        assertEquals(book.getPictureUrl(),"google.com/images");
    }

    @org.junit.jupiter.api.Test
    void setPictureUrl(){
        book.setPictureUrl("instagram.com");
        assertEquals(book.getPictureUrl(),"instagram.com");
    }

    @org.junit.jupiter.api.Test
    void getInventory(){
        assertEquals(book.getInventory(),15);
    }

    @org.junit.jupiter.api.Test
    void setInventory(){
        book.setInventory(10);
        assertEquals(book.getInventory(),10);
    }

    @org.junit.jupiter.api.Test
    void sold(){
        book.sold();
        assertEquals(book.getInventory(), 14);
    }

    @org.junit.jupiter.api.Test
    void isOutOfStock(){
        assertEquals(book.isOutOfStock(), false);
    }

}
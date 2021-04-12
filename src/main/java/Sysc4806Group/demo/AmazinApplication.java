package Sysc4806Group.demo;

import Sysc4806Group.demo.entities.Book;
import Sysc4806Group.demo.repositories.BookRepository;
import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class AmazinApplication {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public FF4j ff4j;

    private static final String FEATURE_SHOW_RECOMMENDATIONS = "showRecommendations";

    public static void main(String[] args) {
        SpringApplication.run(AmazinApplication.class, args);
    }

    @PostConstruct
    public void populateDummyFeatureForMySample() {
        if (!ff4j.exist(FEATURE_SHOW_RECOMMENDATIONS)) {
            ff4j.createFeature(new Feature(FEATURE_SHOW_RECOMMENDATIONS, true));
        }
    }

    @Bean
    InitializingBean sendDatabase() {
        return () -> {
            bookRepository.save(new Book("1408841649", "Harry Potter and the Philosophers Stone", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/16/48/9781408841648.jpg", 12));
            bookRepository.save(new Book("0747586446", "Harry Potter and the Chamber of Secrets", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/64/49/9780747586449.jpg", 11));
            bookRepository.save(new Book("074754557X", "Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/16/48/9781408841648.jpg", 10));
            bookRepository.save(new Book("155192515x", "Harry Potter and the Goblet of Fire", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/51/58/9781551925158.jpg", 12));
            bookRepository.save(new Book("155192516x", "Harry Potter and the Order of the Phoenix", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/94/11/9780747569411.jpg", 12));
            bookRepository.save(new Book("155192517x", "Harry Potter and the Half Blood Prince", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/59/42/9781408855942.jpg", 12));
            bookRepository.save(new Book("140885595x", "Harry Potter and the Deathly Hallows", "J.K. Rowling", "random publisher", "https://images.isbndb.com/covers/59/59/9781408855959.jpg", 12));
            bookRepository.save(new Book("1419741853", "Diary of a Wimpy Kid 1", "J.K. Rowling", "also a random publisher", "https://images.isbndb.com/covers/18/52/9781419741852.jpg", 9));
            bookRepository.save(new Book("0984782850", "Cracking the Coding Interview: 189 Programming Questions and Solutions", "Gayle Lakeman", "another random publisher", "https://images.isbndb.com/covers/28/57/9780984782857.jpg", 9));
            bookRepository.save(new Book("0984782859", "Get Coding with Debugging", "Kevin Wood", "another random publisher", "https://images.isbndb.com/covers/28/57/9780984782857.jpg", 9));
            bookRepository.save(new Book("1410498751", "Diary of a Wimpy Kid: The Last Straw", "Jeff Kinney", "Amulet Books", "https://images.isbndb.com/covers/87/55/9781410498755.jpg", 2));
            bookRepository.save(new Book("0143303848", "Diary of a Wimpy Kid: Rodrick Rules", "Jeff Kinney", "Amulet Books", "https://images.isbndb.com/covers/38/48/9780143303848.jpg", 28));
            bookRepository.save(new Book("1419716336", "Diary of a Wimpy Kid: Dog Days", "Jeff Kinney", "Amulet Books", "https://images.isbndb.com/covers/63/31/9781419716331.jpg", 8));
            bookRepository.save(new Book("1419716436", "Diary of a Wimpy Kid: Cabin Fever", "Jeff Kinney", "Amulet Books", "https://images.isbndb.com/covers/76/30/9780141337630.jpg", 3));
            bookRepository.save(new Book("0751310832", "How Science Works", "Judith Hann", "Eyewitness Science Guides", "https://images.isbndb.com/covers/08/32/9780751310832.jpg", 93));
        };
    }
}
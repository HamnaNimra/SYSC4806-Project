package Sysc4806Group.demo;

import Sysc4806Group.demo.entities.BookStore;
import Sysc4806Group.demo.repositories.BookStoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BookStoreRepository repository){

        BookStore bookstore = new BookStore();

        return args -> {
          log.info("Preloading..." + repository.save(bookstore));
        };

    }

}

package Sysc4806Group.demo;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "bookstore", path = "bookstore")
public interface BookStoreRepository extends PagingAndSortingRepository<Book, Long> {

//    List<Book> findByName(@Param("name") String name);

}

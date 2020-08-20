package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    Book save(Book book);


    Integer countByStatusEqualsAndTitle_TitleEquals(String status, String title);

}

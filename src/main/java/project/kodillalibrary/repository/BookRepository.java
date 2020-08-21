package project.kodillalibrary.repository;

import org.springframework.data.repository.CrudRepository;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.domain.Status;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    Book save(Book book);

    @Override
    Optional<Book> findById(Long id);


    Integer countByStatusEqualsAndTitle_TitleEquals(Status status, String title);

}

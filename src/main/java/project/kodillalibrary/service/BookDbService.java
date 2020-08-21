package project.kodillalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.domain.Status;
import project.kodillalibrary.repository.BookRepository;

import java.util.Optional;

@Service
public class BookDbService {

    private final static Status STATUS = Status.AVAILABLE;

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(final Book book){
        return bookRepository.save(book);
    }

    public Optional<Book> getBook(final Long id){
        return bookRepository.findById(id);
    }

    public Integer numberOfBooksAvailable(String title){
        return bookRepository.countByStatusEqualsAndTitle_TitleEquals(STATUS,title);
    }
}

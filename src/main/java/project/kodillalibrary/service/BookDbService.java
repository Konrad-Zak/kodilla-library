package project.kodillalibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.repository.BookRepository;

@Service
public class BookDbService {

    private final static String STATUS = "Ok";

    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(final Book book){
        return bookRepository.save(book);
    }

    public Integer numberOfBooksAvailable(String title){
        return bookRepository.countByStatusEqualsAndTitle_TitleEquals(STATUS,title);
    }
}

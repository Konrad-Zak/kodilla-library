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
    
    public void changeBookStatus(final Book book){
        Status status;
        if(book.getStatus() == Status.AVAILABLE){
            status = Status.INACCESSIBLE;
        } else {
            status = Status.AVAILABLE;
        }
        saveBook(new Book(book.getId(),book.getTitle(),status));
    }

    public Integer numberOfBooksAvailable(String title){
        return bookRepository.countByStatusEqualsAndTitle_TitleEquals(STATUS,title);
    }
}

package project.kodillalibrary.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.domain.BookDto;
import project.kodillalibrary.domain.Status;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.exception.BookNotFoundException;
import project.kodillalibrary.exception.StatusNotFoundException;
import project.kodillalibrary.exception.TitleNotFoundException;
import project.kodillalibrary.mapper.BookMapper;
import project.kodillalibrary.service.BookDbService;
import project.kodillalibrary.service.TitleDbService;

@Component
public class BookFacade {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookDbService bookDbService;

    @Autowired
    TitleDbService titleDbService;

    public BookDto createBook(Long Id) {
        Title title = titleDbService.getTitle(Id).orElseThrow(TitleNotFoundException::new);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        return bookMapper.mapToBookDto(bookDbService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    public BookDto updateBook(Long Id, String name) {
        try {
            Status status = Status.valueOf(name);
            Book book = bookDbService.getBook(Id).orElseThrow(BookNotFoundException::new);
            BookDto bookDto = new BookDto(book.getId(), book.getTitle(), status);
            return bookMapper.mapToBookDto(bookDbService.saveBook(bookMapper.mapToBook(bookDto)));
        } catch (IllegalArgumentException e) {
            throw new StatusNotFoundException();
        }
    }

    public Integer getQuantityOfBooksByTitle(String title){
        return bookDbService.numberOfBooksAvailable(title);
    }

}

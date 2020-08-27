package project.kodillalibrary.facade;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class BookFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookFacade.class);
    private BookMapper bookMapper;
    private BookDbService bookDbService;
    private TitleDbService titleDbService;

    public BookDto createBook(Long titleId) {
        Title title = titleDbService.getTitle(titleId).orElseThrow(TitleNotFoundException::new);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        LOGGER.info("Create book of title id: "+ title.getId());
        return bookMapper.mapToBookDto(bookDbService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    public BookDto updateBook(Long Id, Status statusName) {
        try {
            Book book = bookDbService.getBook(Id).orElseThrow(BookNotFoundException::new);
            BookDto bookDto = new BookDto(book.getId(), book.getTitle(), statusName);
            LOGGER.info("Book id: " + book.getId() + " change status from: "
                    + book.getStatus() + " on: " + statusName);
            return bookMapper.mapToBookDto(bookDbService.saveBook(bookMapper.mapToBook(bookDto)));
        } catch (IllegalArgumentException e) {
            LOGGER.error("Wrong book_id or status name");
            throw new StatusNotFoundException();
        }
    }

    public int getQuantityOfBooksStatusAvailableByTitle(String title){
        LOGGER.debug("Request for quantity books with title: " + title);
        return bookDbService.numberOfBooksAvailable(title);
    }

    public List<BookDto> getAllBooks(){
        LOGGER.debug("Request for all books");
        return bookMapper.mapToBookDtoList(bookDbService.getAllBooks());
    }

    public List<Status> getAllStatus(){
        LOGGER.debug("Request for book status list");
        return Arrays.asList(Status.values());
    }

}

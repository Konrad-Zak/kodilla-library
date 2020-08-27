package project.kodillalibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.BookDto;
import project.kodillalibrary.domain.Status;
import project.kodillalibrary.facade.BookFacade;

import java.util.List;


@RestController
@RequestMapping("/v1/books")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class BookController {

    private BookFacade bookFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/{titleId}")
    public BookDto createBook(@PathVariable Long titleId){
        return bookFacade.createBook(titleId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.PUT, value = "/{bookId}/{statusName}")
    public BookDto updateBookStatus(@PathVariable Long bookId, @PathVariable Status statusName){
        return bookFacade.updateBook(bookId,statusName);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value = "/{title}")
    public int getQuantityOfBooksAvailableStatusByTitle(@PathVariable String title){
        return bookFacade.getQuantityOfBooksStatusAvailableByTitle(title);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET)
    public List<BookDto> getBooks(){
        return bookFacade.getAllBooks();
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value = "/status")
    public List<Status> getStatusList(){
        return bookFacade.getAllStatus();
    }
}

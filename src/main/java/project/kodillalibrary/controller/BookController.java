package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.domain.BookDto;
import project.kodillalibrary.domain.Status;
import project.kodillalibrary.facade.BookFacade;

import java.util.List;


@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookFacade bookFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/books/{titleId}")
    public BookDto createBook(@PathVariable Long titleId){
        return bookFacade.createBook(titleId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.PUT, value = "/books/{bookId}/{statusName}")
    public BookDto updateBookStatus(@PathVariable Long bookId, @PathVariable String statusName){
        return bookFacade.updateBook(bookId,statusName);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value = "/books/{title}")
    public Integer getQuantityOfBooksByTitle(@PathVariable String title){
        return bookFacade.getQuantityOfBooksByTitle(title);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value = "/books")
    public List<BookDto> getBooks(){
        return bookFacade.getAllBooks();
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(method = RequestMethod.GET, value = "/status")
    public List<Status> getStatusList(){
        return bookFacade.getAllStatus();
    }
}

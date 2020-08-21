package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.domain.BookDto;
import project.kodillalibrary.domain.Status;
import project.kodillalibrary.domain.Title;
import project.kodillalibrary.exception.BookNotFoundException;
import project.kodillalibrary.exception.TitleNotFoundException;
import project.kodillalibrary.mapper.BookMapper;
import project.kodillalibrary.service.BookDbService;
import project.kodillalibrary.service.TitleDbService;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookDbService bookDbService;

    @Autowired
    TitleDbService titleDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/books")
    public BookDto createBook(@RequestParam Long titleId){
        Title title = titleDbService.getTitle(titleId).orElseThrow(TitleNotFoundException::new);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        return bookMapper.mapToBookDto(bookDbService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.PUT, value = "/books")
    public BookDto updateBook(@RequestParam Long bookId){
        BookDto bookDto = bookMapper.mapToBookDto(bookDbService.getBook(bookId).orElseThrow(BookNotFoundException::new));
        if(bookDto.getStatus() == Status.AVAILABLE){
            bookDto.setStatus(Status.INACCESSIBLE);
        } else {
            bookDto.setStatus(Status.AVAILABLE);
        }
        return bookMapper.mapToBookDto(bookDbService.saveBook(bookMapper.mapToBook(bookDto)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books/{title}")
    public Integer getBookByTitle(@PathVariable String title){
        return bookDbService.numberOfBooksAvailable(title);
    }
}

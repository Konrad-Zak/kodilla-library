package project.kodillalibrary.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.BookDto;
import project.kodillalibrary.domain.Title;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class BookController {

    @RequestMapping(method = RequestMethod.POST, value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody BookDto bookDto){
        return bookDto;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@RequestBody BookDto bookDto){
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/books/{title}")
    public Integer getBookByTitle(@PathVariable String title){
        return 3;
    }
}

package project.kodillalibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.exception.ReaderNotFoundException;
import project.kodillalibrary.exception.RentNotFoundException;
import project.kodillalibrary.exception.WrongSelectedBookException;
import project.kodillalibrary.mapper.RentMapper;
import project.kodillalibrary.service.BookDbService;
import project.kodillalibrary.service.ReaderDbService;
import project.kodillalibrary.service.RentDbService;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "*")
public class RentController {

    @Autowired
    private RentMapper rentMapper;


    @Autowired
    private RentDbService rentDbService;

    @Autowired
    private BookDbService bookDbService;

    @Autowired
    private ReaderDbService readerDbService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, value = "/rents")
    public RentDto createRent(@RequestParam Long readerId , @RequestParam Long bookId){
        Reader reader = readerDbService.getReader(readerId).orElseThrow(ReaderNotFoundException::new);
        Book book = bookDbService.getBook(bookId).orElseThrow(ReaderNotFoundException::new);
        if(book.getStatus() == Status.AVAILABLE) {
            RentDto rentDto = new RentDto(book, reader, LocalDate.now(), LocalDate.now().plusDays(7));
            bookDbService.changeBookStatus(book);
            return rentMapper.mapToRentDto(rentDbService.saveRent(rentMapper.mapToRent(rentDto)));
        } else {
            throw new WrongSelectedBookException();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/rents")
    public void deleteRent(@RequestParam Long rentId){
        Rent rent = rentDbService.getRent(rentId).orElseThrow(RentNotFoundException::new);
        Book book = rent.getBook();
        bookDbService.changeBookStatus(book);
        rentDbService.deleteRent(rentId);
    }
}

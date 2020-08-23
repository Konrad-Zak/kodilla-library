package project.kodillalibrary.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.exception.ReaderNotFoundException;
import project.kodillalibrary.exception.RentNotFoundException;
import project.kodillalibrary.exception.WrongSelectedBookException;
import project.kodillalibrary.mapper.RentMapper;
import project.kodillalibrary.service.BookDbService;
import project.kodillalibrary.service.ReaderDbService;
import project.kodillalibrary.service.RentDbService;

import java.time.LocalDate;
import java.util.List;

@Component
public class RentFacade {

    private final static LocalDate DATE_OF_RENT = LocalDate.now();
    private final static LocalDate DATE_OF_DELIVER = LocalDate.now().plusDays(14);

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private RentDbService rentDbService;

    @Autowired
    private BookDbService bookDbService;

    @Autowired
    private ReaderDbService readerDbService;

    public RentDto createRent(Long readerId, Long bookId){
        Reader reader = readerDbService.getReader(readerId).orElseThrow(ReaderNotFoundException::new);
        Book book = bookDbService.getBook(bookId).orElseThrow(ReaderNotFoundException::new);
        if(book.getStatus() == Status.AVAILABLE) {
            RentDto rentDto = new RentDto(book, reader, DATE_OF_RENT, DATE_OF_DELIVER);
            bookDbService.changeBookStatus(book);
            return rentMapper.mapToRentDto(rentDbService.saveRent(rentMapper.mapToRent(rentDto)));
        } else {
            throw new WrongSelectedBookException();
        }
    }

    public List<RentDto> getAllRents(){
        return rentMapper.mapToRentDtoList(rentDbService.getAllRents());
    }

    public void deleteRent(Long Id){
        Rent rent = rentDbService.getRent(Id).orElseThrow(RentNotFoundException::new);
        Book book = rent.getBook();
        bookDbService.changeBookStatus(book);
        rentDbService.deleteRent(Id);
    }
}

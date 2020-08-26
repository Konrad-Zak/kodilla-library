package project.kodillalibrary.facade;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@AllArgsConstructor
public class RentFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(RentFacade.class);
    private static final LocalDate DATE_OF_RENT = LocalDate.now();
    private static final LocalDate DATE_OF_DELIVER = LocalDate.now().plusDays(14);
    private RentMapper rentMapper;
    private RentDbService rentDbService;
    private BookDbService bookDbService;
    private ReaderDbService readerDbService;

    public RentDto createRent(Long readerId, Long bookId){
        Reader reader = readerDbService.getReader(readerId).orElseThrow(ReaderNotFoundException::new);
        Book book = bookDbService.getBook(bookId).orElseThrow(ReaderNotFoundException::new);
        if(book.getStatus() == Status.AVAILABLE) {
            RentDto rentDto = new RentDto(book, reader, DATE_OF_RENT, DATE_OF_DELIVER);
            bookDbService.changeBookStatus(book);
            LOGGER.info("Create new rent for: " + readerId + " book: " + bookId);
            LOGGER.info("Change book status to: " + book.getStatus());
            return rentMapper.mapToRentDto(rentDbService.saveRent(rentMapper.mapToRent(rentDto)));
        } else {
            LOGGER.error("Bad request... wrong reader Id: " + readerId +  " or book Id: " + bookId);
            throw new WrongSelectedBookException();
        }
    }

    public List<RentDto> getAllRents(){
        LOGGER.debug("Request: get all rents");
        return rentMapper.mapToRentDtoList(rentDbService.getAllRents());
    }

    public void deleteRent(Long rentId){
        Rent rent = rentDbService.getRent(rentId).orElseThrow(RentNotFoundException::new);
        Book book = rent.getBook();
        bookDbService.changeBookStatus(book);
        LOGGER.info("Delete rent of id: " + rent.getId());
        LOGGER.info("Change book: " + book.getId() + " status to: " + book.getStatus());
        rentDbService.deleteRent(rentId);
    }
}

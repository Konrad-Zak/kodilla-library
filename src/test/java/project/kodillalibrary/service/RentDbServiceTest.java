package project.kodillalibrary.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.mapper.BookMapper;
import project.kodillalibrary.mapper.ReaderMapper;
import project.kodillalibrary.mapper.RentMapper;
import project.kodillalibrary.mapper.TitleMapper;
import project.kodillalibrary.repository.BookRepository;
import project.kodillalibrary.repository.ReaderRepository;
import project.kodillalibrary.repository.RentRepository;
import project.kodillalibrary.repository.TitleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RentDbServiceTest {

    @Autowired
    private RentDbService rentDbService;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TitleMapper titleMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private RentMapper rentMapper;

    @Test
    public void saveRent() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        Book book = bookMapper.mapToBook(bookDto);
        bookRepository.save(book);
        ReaderDto readerDto = new ReaderDto(
                "John","Rambo", LocalDate.of(2010,10,20));
        Reader reader = readerMapper.mapToReader(readerDto);
        readerRepository.save(reader);
        RentDto rentDto = new RentDto(book,reader,LocalDate.now(),LocalDate.now().plusDays(14));
        //When
        rentDbService.saveRent(rentMapper.mapToRent(rentDto));
        List<Rent> rents = rentRepository.findAll();
        //Then
        assertEquals(1,rents.size());
        //CleanUp
        rentRepository.deleteAll();
        readerRepository.deleteAll();
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void getRent() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        Book book = bookMapper.mapToBook(bookDto);
        bookRepository.save(book);
        ReaderDto readerDto = new ReaderDto(
                "John","Rambo", LocalDate.of(2010,10,20));
        Reader reader = readerMapper.mapToReader(readerDto);
        readerRepository.save(reader);
        RentDto rentDto = new RentDto(book,reader,LocalDate.now(),LocalDate.now().plusDays(14));
        Rent rent = rentMapper.mapToRent(rentDto);
        rentRepository.save(rent);
        //When
        Optional<Rent> resultRent = rentDbService.getRent(rent.getId());
        //Then
        assertTrue(resultRent.isPresent());
        assertEquals("Test", resultRent.get().getBook().getTitle().getTitle());
        assertEquals("Daniel", resultRent.get().getBook().getTitle().getAuthor());
        assertEquals(2000, resultRent.get().getBook().getTitle().getYear());
        assertEquals(Status.AVAILABLE, resultRent.get().getBook().getStatus());
        assertEquals("John", resultRent.get().getReader().getFirstName());
        assertEquals("Rambo", resultRent.get().getReader().getLastName());
        assertEquals(
                LocalDate.of(2010,10,20),resultRent.get().getReader().getRegistrationDate());
        assertEquals(LocalDate.now(), resultRent.get().getRent());
        assertEquals(LocalDate.now().plusDays(14),resultRent.get().getDeliver());
        //CleanUp
        rentRepository.deleteAll();
        readerRepository.deleteAll();
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void deleteRent() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        Book book = bookMapper.mapToBook(bookDto);
        bookRepository.save(book);
        ReaderDto readerDto = new ReaderDto(
                "John","Rambo", LocalDate.of(2010,10,20));
        Reader reader = readerMapper.mapToReader(readerDto);
        readerRepository.save(reader);
        RentDto rentDto = new RentDto(book,reader,LocalDate.now(),LocalDate.now().plusDays(14));
        Rent rent = rentMapper.mapToRent(rentDto);
        rentRepository.save(rent);
        //When
        rentDbService.deleteRent(rent.getId());
        //Then
        assertFalse(rentRepository.existsById(rent.getId()));
        //CleanUp
        rentRepository.deleteAll();
        readerRepository.deleteAll();
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void getAllRents() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        TitleDto titleDto1 = new TitleDto("Test 2", "Daniel 2", 2001);
        Title title1 = titleMapper.mapToTitle(titleDto1);
        titleRepository.save(title);
        titleRepository.save(title1);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        BookDto bookDto1 = new BookDto(title1, Status.RESERVED);
        Book book = bookMapper.mapToBook(bookDto);
        Book book1 = bookMapper.mapToBook(bookDto1);
        bookRepository.save(book);
        bookRepository.save(book1);
        ReaderDto readerDto = new ReaderDto(
                "John","Rambo", LocalDate.of(2010,10,20));
        Reader reader = readerMapper.mapToReader(readerDto);
        readerRepository.save(reader);
        RentDto rentDto = new RentDto(book,reader,LocalDate.now(),LocalDate.now().plusDays(14));
        RentDto rentDto1 = new RentDto(book1,reader,LocalDate.now(),LocalDate.now().plusDays(14));
        rentRepository.save(rentMapper.mapToRent(rentDto));
        rentRepository.save(rentMapper.mapToRent(rentDto1));
        //When
        List<Rent> rents = rentDbService.getAllRents();
        //Then
        assertEquals("Test", rents.get(0).getBook().getTitle().getTitle());
        assertEquals("Daniel", rents.get(0).getBook().getTitle().getAuthor());
        assertEquals(2000, rents.get(0).getBook().getTitle().getYear());
        assertEquals(Status.AVAILABLE, rents.get(0).getBook().getStatus());
        assertEquals("Test 2", rents.get(1).getBook().getTitle().getTitle());
        assertEquals("Daniel 2", rents.get(1).getBook().getTitle().getAuthor());
        assertEquals(2001, rents.get(1).getBook().getTitle().getYear());
        assertEquals(Status.RESERVED, rents.get(1).getBook().getStatus());
        assertEquals("John", rents.get(0).getReader().getFirstName());
        assertEquals("Rambo", rents.get(0).getReader().getLastName());
        assertEquals(
                LocalDate.of(2010,10,20),rents.get(0).getReader().getRegistrationDate());
        assertEquals(LocalDate.now(), rents.get(0).getRent());
        assertEquals(LocalDate.now().plusDays(14),rents.get(0).getDeliver());
        //CleanUp
        rentRepository.deleteAll();
        readerRepository.deleteAll();
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }
}
package project.kodillalibrary.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import project.kodillalibrary.domain.*;
import project.kodillalibrary.mapper.BookMapper;
import project.kodillalibrary.mapper.TitleMapper;
import project.kodillalibrary.repository.BookRepository;
import project.kodillalibrary.repository.TitleRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookDbServiceTest {

    @Autowired
    private BookDbService bookDbService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private TitleMapper titleMapper;

    @Autowired
    private TitleRepository titleRepository;

    @Test
    public void saveBook() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        //When
        bookDbService.saveBook(bookMapper.mapToBook(bookDto));
        List<Book> books = bookRepository.findAll();
        //Then
        assertEquals(1,books.size());
        //CleanUp
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void getBook() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        Book book = bookMapper.mapToBook(bookDto);
        bookRepository.save(book);
        //When
        Optional<Book> bookResult = bookDbService.getBook(book.getId());
        //Then
        assertTrue(bookResult.isPresent());
        assertEquals("Test", bookResult.get().getTitle().getTitle());
        assertEquals("Daniel", bookResult.get().getTitle().getAuthor());
        assertEquals(2000, bookResult.get().getTitle().getYear());
        assertEquals(Status.AVAILABLE, bookResult.get().getStatus());
        //CleanUp
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void getAllBooks() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        BookDto bookDto1 = new BookDto(title, Status.RESERVED);
        bookRepository.save(bookMapper.mapToBook(bookDto));
        bookRepository.save(bookMapper.mapToBook(bookDto1));
        //When
        List<Book> books = bookDbService.getAllBooks();
        //Then
        assertEquals(2, books.size());
        assertEquals("Test", books.get(0).getTitle().getTitle());
        assertEquals("Daniel", books.get(0).getTitle().getAuthor());
        assertEquals(2000, books.get(0).getTitle().getYear());
        assertEquals(Status.AVAILABLE, books.get(0).getStatus());
        assertEquals(Status.RESERVED, books.get(1).getStatus());
        //CleanUp
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void changeBookStatus() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.INACCESSIBLE);
        Book book = bookMapper.mapToBook(bookDto);
        bookRepository.save(book);
        //When
        bookDbService.changeBookStatus(book);
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        //Then
        assertTrue(optionalBook.isPresent());
        assertEquals(Status.AVAILABLE, optionalBook.get().getStatus());
        //CleanUp
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }

    @Test
    public void numberOfBooksAvailable() {
        //Given
        TitleDto titleDto = new TitleDto("Test", "Daniel", 2000);
        Title title = titleMapper.mapToTitle(titleDto);
        titleRepository.save(title);
        BookDto bookDto = new BookDto(title, Status.AVAILABLE);
        BookDto bookDto1 = new BookDto(title, Status.AVAILABLE);
        bookRepository.save(bookMapper.mapToBook(bookDto));
        bookRepository.save(bookMapper.mapToBook(bookDto1));
        //When
        int result = bookDbService.numberOfBooksAvailable("Test");
        //Then
        assertEquals(2,result);
        //CleanUp
        bookRepository.deleteAll();
        titleRepository.deleteAll();
    }
}
package project.kodillalibrary.mapper;

import org.springframework.stereotype.Component;
import project.kodillalibrary.domain.Book;
import project.kodillalibrary.domain.BookDto;

@Component
public class BookMapper {

    public Book mapToBook(BookDto bookDto){
        return new Book(bookDto.getId(),bookDto.getTitle(),bookDto.getStatus());
    }

    public BookDto mapToBookDto(Book book){
        return new BookDto(book.getId(),book.getTitle(),book.getStatus());
    }
}

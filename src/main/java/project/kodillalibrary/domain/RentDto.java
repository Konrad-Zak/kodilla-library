package project.kodillalibrary.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RentDto {
    private Long id;
    private Book book;
    private Reader reader;
    private LocalDate rent;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deliver;

    public RentDto(Book book, Reader reader, LocalDate rent, LocalDate deliver) {
        this.book = book;
        this.reader = reader;
        this.rent = rent;
        this.deliver = deliver;
    }

}

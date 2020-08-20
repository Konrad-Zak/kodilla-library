package project.kodillalibrary.domain;

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
    private LocalDate deliver;
}
